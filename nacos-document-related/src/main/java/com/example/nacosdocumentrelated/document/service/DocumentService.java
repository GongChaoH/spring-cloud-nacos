package com.example.nacosdocumentrelated.document.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DocumentService {

    public void download(String path, HttpServletResponse response);

    public void onLine(String filePath, HttpServletResponse response, boolean isOnLine);

    public List<String> getFiles(String path);
}
