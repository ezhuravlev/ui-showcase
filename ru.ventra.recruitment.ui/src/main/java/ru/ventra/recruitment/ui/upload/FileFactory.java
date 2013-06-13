package ru.ventra.recruitment.ui.upload;

import java.io.File;

public interface FileFactory {
	public File createFile(String fileName, String mimeType);
}