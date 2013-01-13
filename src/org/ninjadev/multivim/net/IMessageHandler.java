package org.ninjadev.multivim.net;

import org.ninjadev.multivim.Message;

public interface IMessageHandler {
	public void processMessage(Message message) throws Exception;
}
