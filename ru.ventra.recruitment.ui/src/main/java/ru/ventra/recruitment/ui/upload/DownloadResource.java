package ru.ventra.recruitment.ui.upload;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.vaadin.server.DownloadStream;
import com.vaadin.server.StreamResource;

public class DownloadResource extends StreamResource {
	private static final long serialVersionUID = 1L;
	
	

	public DownloadResource(byte[] bytes) {
		super(new ByteArrayStreamResource(bytes), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.terminal.StreamResource#getStream()
	 */
	@Override
	public DownloadStream getStream() {

		final DownloadStream stream = new DownloadStream(getStreamSource().getStream(), getMIMEType(), getFilename());

		stream.setParameter("Content-Disposition", "attachment;filename=" + getFilename());
		// This magic incantation should prevent anyone from caching the data
		stream.setParameter("Cache-Control", "private,no-cache,no-store");
		// In theory <=0 disables caching. In practice Chrome, Safari (and,
		// apparently, IE) all
		// ignore <=0. Set to 1s
		stream.setCacheTime(1000);

		return stream;
	}
	
	private static class ByteArrayStreamResource implements StreamResource.StreamSource {
		private static final long serialVersionUID = 1L;

		ByteArrayInputStream inputStream;

		public ByteArrayStreamResource(byte[] bytes) {
			inputStream = new ByteArrayInputStream(bytes);
		}
		
		@Override
		public InputStream getStream() {
			return inputStream;
		}
	}
}