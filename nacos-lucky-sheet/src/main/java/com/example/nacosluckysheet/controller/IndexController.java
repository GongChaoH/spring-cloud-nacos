package com.example.nacosluckysheet.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.nacosluckysheet.entity.WorkBookEntity;
import com.example.nacosluckysheet.entity.WorkSheetEntity;
import com.example.nacosluckysheet.repository.WorkBookRepository;
import com.example.nacosluckysheet.repository.WorkSheetRepository;
import com.example.nacosluckysheet.utils.SheetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Mars
 * @date 2020/10/28
 * @description
 */
@Controller
public class IndexController {

    @Autowired
    private WorkBookRepository workBookRepository;

    @Autowired
    private WorkSheetRepository workSheetRepository;

    @GetMapping("index")
    @ResponseBody
    public ModelAndView index() {
        List<WorkBookEntity> all = workBookRepository.findAll();

        return new ModelAndView("index", "all", all);
    }


    @GetMapping("index/create")
    @ResponseBody
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WorkBookEntity wb = new WorkBookEntity();
        wb.setName("default");
        wb.setOption(SheetUtil.getDefautOption());
        WorkBookEntity saveWb = workBookRepository.save(wb);
        //生成sheet数据
        generateSheet(saveWb.getId());
        response.sendRedirect("/index/" + saveWb.getId());
    }

    @GetMapping("/delete/{wbId}")
    public String delete(@PathVariable(value = "wbId") String wbId){
        List<WorkSheetEntity> workSheetEntities = workSheetRepository.findAllBywbId(wbId);
        if (!CollectionUtils.isEmpty(workSheetEntities)){
            for (WorkSheetEntity ws : workSheetEntities){
                workSheetRepository.deleteById(ws.getId());
            }
        }
        workBookRepository.deleteById(wbId);
        return "redirect:/index";
    }


    @GetMapping("/index/{wbId}")
    @ResponseBody
    public ModelAndView index(@PathVariable(value = "wbId") String wbId) {
        Optional<WorkBookEntity> Owb = workBookRepository.findById(wbId);
        WorkBookEntity wb = new WorkBookEntity();
        if (!Owb.isPresent()) {
            wb.setId(wbId);
            wb.setName("default");
            wb.setOption(SheetUtil.getDefautOption());
            WorkBookEntity result = workBookRepository.save(wb);
            generateSheet(wbId);
        } else {
            wb = Owb.get();
        }

        return new ModelAndView("websocket", "wb", wb);
    }

    @PostMapping("/load/{wbId}")
    @ResponseBody
    public String load(@PathVariable(value = "wbId") String wbId) {

        List<WorkSheetEntity> wsList = workSheetRepository.findAllBywbId(wbId);
        List<JSONObject> list = new ArrayList<>();
        wsList.forEach(ws -> {
            list.add(ws.getData());
        });


        return JSONUtil.toJsonStr(list);
    }


    @PostMapping("/loadSheet/{wbId}")
    @ResponseBody
    public String loadSheet(@PathVariable(value = "wbId") String wbId) {
        List<WorkSheetEntity> wsList = workSheetRepository.findAllBywbId(wbId);
        List<JSONObject> list = new ArrayList<>();
        wsList.forEach(ws -> {
            list.add(ws.getData());
        });
        if (!list.isEmpty()) {
            return SheetUtil.buildSheetData(list).toString();
        }
        return SheetUtil.getDefaultAllSheetData().toString();
    }


    private void generateSheet(String wbId) {
        SheetUtil.getDefaultSheetData().forEach(jsonObject -> {
            WorkSheetEntity ws = new WorkSheetEntity();
            ws.setWbId(wbId);
            ws.setData(jsonObject);
            ws.setDeleteStatus(0);
            workSheetRepository.save(ws);
        });
    }


    @GetMapping("read/{name}")
    public void readImageIo(@PathVariable(value = "name") String name,
                            HttpServletRequest request, HttpServletResponse response){
        ServletOutputStream out = null;
        FileInputStream fis = null;
        try {
            String path = getPath(name);
            File file = new File(path);
            fis = new FileInputStream(file);
            response.setContentType("image/png");
            out = response.getOutputStream();
            int len = 0;
            byte[] bt = new byte[1024 * 10];
            while ((len = fis.read(bt)) != -1){
                out.write(bt, 0 , len);
            }
            out.flush();
        } catch (Exception e) {

        } finally {
            try {
                out.close();
                fis.close();
            } catch (IOException e) {

            }
        }
    }

    private String getPath(String name) {
        String path = this.getClass().getClassLoader().getResource("static/img/" + name).getPath();
        return path;
    }

}
