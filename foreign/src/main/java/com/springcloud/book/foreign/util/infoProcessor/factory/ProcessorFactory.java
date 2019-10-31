package com.springcloud.book.foreign.util.infoProcessor.factory;

import com.springcloud.book.foreign.util.infoProcessor.processor.Processor;
import com.springcloud.book.foreign.util.infoProcessor.processor.impl.EndNoteProcessor;
import com.springcloud.book.foreign.util.infoProcessor.processor.impl.MyselfSetProcessor;
import com.springcloud.book.foreign.util.infoProcessor.processor.impl.NoteExpressProcessor;

/**
 *
 * @author Ryan
 * Processor工厂
 */
public class ProcessorFactory {
	public static Processor getProcessor(String processorName){
		Processor p=null;
		if(processorName.equals("EndNote"))
			p = new EndNoteProcessor();
		if(processorName.equals("NoteExpress"))
			p = new NoteExpressProcessor();
		if (processorName.equals("MyselfSet"))
			p = new MyselfSetProcessor();
		return p;
	}

}
