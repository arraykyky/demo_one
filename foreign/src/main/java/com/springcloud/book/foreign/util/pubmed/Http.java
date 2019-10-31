package com.springcloud.book.foreign.util.pubmed;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.zip.GZIPInputStream;

public class Http {
    private CookieManager cookieManager = new CookieManager();
    private HashMap<String, String> defaultHeaders = new HashMap();
    private Proxy proxy;
    private int timeOut = 50000;

    public Http() {
        CookieHandler.setDefault(this.cookieManager);
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public void setDefaultHeaders(HashMap<String, String> headers) {
        this.defaultHeaders = headers;
    }

    public void download(int threadCount, final String urlStr, final String savePath, final HashMap<String, String> headers) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn;
            if (this.proxy != null) {
                conn = (HttpURLConnection) url.openConnection(this.proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }

            conn.setConnectTimeout(this.timeOut);
            conn.setRequestMethod("HEAD");
            Iterator var7;
            String key;
            if (headers != null) {
                headers.putAll(this.defaultHeaders);
                var7 = headers.keySet().iterator();

                while (var7.hasNext()) {
                    key = (String) var7.next();
                    conn.setRequestProperty(key, (String) headers.get(key));
                }
            } else {
                var7 = this.defaultHeaders.keySet().iterator();

                while (var7.hasNext()) {
                    key = (String) var7.next();
                    conn.setRequestProperty(key, (String) this.defaultHeaders.get(key));
                }
            }

            String contentLengthStr = conn.getHeaderField("Content-Length");
            long contentLength = Long.valueOf(contentLengthStr);
            long range = (long) Math.floor((double) (contentLength / (long) threadCount));
            boolean flag = false;
            final CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; ++i) {
                long start = 0L;
                long end = 0L;
                if (i != threadCount - 1) {
                    start = range * (long) i;
                    end = range * (long) i + range;
                } else {
                    start = range * (long) i;
                    end = contentLength - range * (long) (threadCount - 1);
                }

                Thread thread = new Thread(start + "-" + end) {
                    public void run() {
                        try {
                            URL url = new URL(urlStr);
                            URLConnection conn = url.openConnection();
                            if (headers != null) {
                                Iterator var3 = headers.keySet().iterator();

                                while (var3.hasNext()) {
                                    String key = (String) var3.next();
                                    conn.setRequestProperty(key, (String) headers.get(key));
                                }
                            }

                            conn.setRequestProperty("Range", "bytes=" + this.getName());
                            File file = new File(savePath.substring(0, savePath.lastIndexOf(".")) + this.getName() + ".mp4");
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }

                            FileOutputStream fos = new FileOutputStream(file);
                            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                            byte[] buf = new byte[5120];

                            int readed;
                            while ((readed = bis.read(buf, 0, buf.length)) != -1) {
                                fos.write(buf, 0, readed);
                                fos.flush();
                            }

                            fos.flush();
                            fos.close();
                            bis.close();
                        } catch (Exception var8) {
                            var8.printStackTrace();
                        }

                        latch.countDown();
                    }
                };
                thread.start();
            }

            latch.await();
        } catch (Exception var20) {
            var20.printStackTrace();
        }

    }

    public void download(String urlStr, String savePath, HashMap<String, String> headers) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn;
            if (this.proxy != null) {
                conn = (HttpURLConnection) url.openConnection(this.proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }

            Iterator var6;
            String key;
            if (headers != null) {
                headers.putAll(this.defaultHeaders);
                var6 = headers.keySet().iterator();

                while (var6.hasNext()) {
                    key = (String) var6.next();
                    conn.setRequestProperty(key, (String) headers.get(key));
                }
            } else {
                var6 = this.defaultHeaders.keySet().iterator();

                while (var6.hasNext()) {
                    key = (String) var6.next();
                    conn.setRequestProperty(key, (String) this.defaultHeaders.get(key));
                }
            }

            File file = new File(savePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            byte[] buf = new byte[5120];

            int readed;
            while ((readed = bis.read(buf, 0, buf.length)) != -1) {
                fos.write(buf, 0, readed);
                fos.flush();
            }

            fos.flush();
            fos.close();
            bis.close();
        } catch (Exception var11) {
            var11.printStackTrace();
        }

    }

    public Http.Response get(String urlStr, String charset, HashMap<String, String> headers) {
        Http.Response response = new Http.Response();
        HttpURLConnection conn = null;

        try {
            URL url = new URL(urlStr);
            if (this.proxy != null) {
                conn = (HttpURLConnection) url.openConnection(this.proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }

            conn.setConnectTimeout(this.timeOut);
            conn.setReadTimeout(this.timeOut);
            Iterator var7;
            String readedLine;
            if (headers != null) {
                headers.putAll(this.defaultHeaders);
                var7 = headers.keySet().iterator();

                while (var7.hasNext()) {
                    readedLine = (String) var7.next();
                    conn.setRequestProperty(readedLine, (String) headers.get(readedLine));
                }
            } else {
                var7 = this.defaultHeaders.keySet().iterator();

                while (var7.hasNext()) {
                    readedLine = (String) var7.next();
                    conn.setRequestProperty(readedLine, (String) this.defaultHeaders.get(readedLine));
                }
            }

            BufferedReader reader;
            if ("gzip".equals(conn.getContentEncoding())) {
                reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream()), charset));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            }

            StringBuffer content = new StringBuffer();

            while ((readedLine = reader.readLine()) != null) {
                content.append(readedLine);
            }

            response.code = conn.getResponseCode();
            response.content = content.toString();
        } catch (Exception var10) {
            var10.printStackTrace();
            response.code = -1;
            response.error = var10.getLocalizedMessage();
            if (conn != null) {
                ;
            }
        }

        return response;
    }

    public Http.Response post(String urlStr, String charset, HashMap<String, String> headers, HashMap<String, String> params) {
        Http.Response response = new Http.Response();
        HttpURLConnection conn = null;

        try {
            URL url = new URL(urlStr);
            if (this.proxy != null) {
                conn = (HttpURLConnection) url.openConnection(this.proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }

            conn.setConnectTimeout(this.timeOut);
            conn.setReadTimeout(this.timeOut);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            Iterator var8;
            String readedLine;
            if (headers != null) {
                headers.putAll(this.defaultHeaders);
                var8 = headers.keySet().iterator();

                while (var8.hasNext()) {
                    readedLine = (String) var8.next();
                    conn.setRequestProperty(readedLine, (String) headers.get(readedLine));
                }
            } else {
                var8 = this.defaultHeaders.keySet().iterator();

                while (var8.hasNext()) {
                    readedLine = (String) var8.next();
                    conn.setRequestProperty(readedLine, (String) this.defaultHeaders.get(readedLine));
                }
            }

            if (params != null) {
                StringBuffer paramsStr = new StringBuffer();
                Iterator var15 = params.keySet().iterator();

                while (var15.hasNext()) {
                    String key = (String) var15.next();
                    Object value = params.get(key);
                    paramsStr.append(key + "=" + URLEncoder.encode(value.toString(), charset) + "&");
                }

                PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
                printWriter.write(paramsStr.toString());
                printWriter.flush();
            }

            BufferedReader reader;
            if ("gzip".equals(conn.getContentEncoding())) {
                reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream()), charset));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            }

            StringBuffer content = new StringBuffer();

            while ((readedLine = reader.readLine()) != null) {
                content.append(readedLine + "\n");
            }

            response.code = conn.getResponseCode();
            response.content = content.toString();
        } catch (Exception var12) {
            var12.printStackTrace();
            response.code = -1;
            response.error = var12.getLocalizedMessage();
            if (conn != null) {
                ;
            }
        }

        return response;
    }

    public Http.Response post(String urlStr, String charset, HashMap<String, String> headers, String params) {
        Http.Response response = new Http.Response();
        HttpURLConnection conn = null;

        try {
            URL url = new URL(urlStr);
            if (this.proxy != null) {
                conn = (HttpURLConnection) url.openConnection(this.proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }

            conn.setConnectTimeout(this.timeOut);
            conn.setReadTimeout(this.timeOut);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            Iterator var8;
            String readedLine;
            if (headers != null) {
                headers.putAll(this.defaultHeaders);
                var8 = headers.keySet().iterator();

                while (var8.hasNext()) {
                    readedLine = (String) var8.next();
                    conn.setRequestProperty(readedLine, (String) headers.get(readedLine));
                }
            } else {
                var8 = this.defaultHeaders.keySet().iterator();

                while (var8.hasNext()) {
                    readedLine = (String) var8.next();
                    conn.setRequestProperty(readedLine, (String) this.defaultHeaders.get(readedLine));
                }
            }

            if (params != null) {
                PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
                printWriter.write(params);
                printWriter.flush();
            }

            BufferedReader reader;
            if ("gzip".equals(conn.getContentEncoding())) {
                reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream()), charset));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            }

            StringBuffer content = new StringBuffer();

            while ((readedLine = reader.readLine()) != null) {
                content.append(readedLine + "\r\n");
            }

            response.code = conn.getResponseCode();
            response.content = content.toString();
        } catch (Exception var11) {
            var11.printStackTrace();
            response.code = -1;
            response.error = var11.getLocalizedMessage();
            if (conn != null) {
                ;
            }
        }

        return response;
    }

    public class Response {
        public int code;
        public String content;
        public String error;

        public Response() {
        }
    }
}
