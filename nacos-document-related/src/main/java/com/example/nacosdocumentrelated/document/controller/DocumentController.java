package com.example.nacosdocumentrelated.document.controller;


import com.example.nacosdocumentrelated.document.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;


    @GetMapping(value = "/document/download")
    public void download(@RequestParam String path, HttpServletResponse response) {
        documentService.download(path, response);
    }


    @GetMapping(value = "/document/onLine")
    public void onLine(@RequestParam String path, HttpServletResponse response){
        documentService.onLine(path, response, true);
    }

    @GetMapping(value = "/document/find-files")
    public List<String> getFiles(){
        return documentService.getFiles(null);
    }
}
