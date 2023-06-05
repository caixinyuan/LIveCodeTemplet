package com.cxy.livecodetemplet.Util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.cxy.livecodetemplet.model.CodeTempletModel;
import com.cxy.livecodetemplet.model.MarkdownTableModel;
import com.cxy.livecodetemplet.services.LiveCodeTempletService;
import com.cxy.livecodetemplet.storage.LiveCodeTempletStorageSetting;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.stream.IntStream;

import static cn.hutool.core.util.CharsetUtil.CHARSET_UTF_8;

public class Util {

    public final static URI defaultLocalMdFilePath = Paths.get(PathManager.getConfigPath(), "CodeTemplet.md").toUri();

    public final static String defaultRemoteURL = "https://raw.githubusercontent.com/caixinyuan/LiveCodeTemplet/main/CodeTemplet.md";

    private static final Logger log = Logger.getInstance(Util.class);


    /**
     * 保存配置
     *
     * @param path 文件地址
     */
    public static void savePathSetting(String path) {
        LiveCodeTempletStorageSetting storageSetting = LiveCodeTempletStorageSetting.getInstance();
        if (storageSetting.getState() == null || !StringUtils.equals(storageSetting.getState().getUrl(), path)) {
            storageSetting.getState().setUrl(path);
            ApplicationManager.getApplication().saveSettings();
        }
    }

    /**
     * 下载远程文件
     *
     * @param url 远程地址
     * @return 已下载的文件
     * @throws Exception
     */
    public static File downLoadRemoteMdFile(String url) throws Exception {
        File mdFile = new File(defaultLocalMdFilePath);
        long fileSize = HttpUtil.downloadFile(url, mdFile, -1);
        if (fileSize > 0 && mdFile.exists()) {
            PluginMessage.notifyInfo("已从远程地址下载模板");
            return mdFile;
        }
        throw new Exception("FileDownloadFail");
    }

    /**
     * 加载远程模板
     */
    public static void loadRemoteMd(String url) {
        ProgressManager.getInstance().run(new Task.Backgroundable(null, "正在从远程地址下载文件") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                try {
                    File mdFile = downLoadRemoteMdFile(url);
                    setCodeTempletList(getCodeTempletList(mdFile));
                    PluginMessage.notifyInfo("加载模板完成");
                } catch (Exception ex) {
                    log.error("从远程加载模板超时 url:[{}]", ex, url);
                    PluginMessage.notifyError(String.format("从远程加载模板超时 url:[%s]", url));
                }
            }
        });
    }

    /**
     * 加载本地模板
     */
    public static void loadLocalMd(String localFilePath) {
        ProgressManager.getInstance().run(new Task.Backgroundable(null, "正在从本地地址加载模板") {
            @Override
            public void run(@NotNull ProgressIndicator indicator) {
                try {
                    File mdFile = new File(localFilePath);
                    File defaultLocalMdFile = new File(defaultLocalMdFilePath);
                    if (mdFile.exists() && mdFile.isFile()) {
                        FileUtil.copy(mdFile, defaultLocalMdFile, true);
                    }
                    List<CodeTempletModel> codeTempletList = getCodeTempletList(defaultLocalMdFile);
                    if (codeTempletList.size() > 0) {
                        setCodeTempletList(codeTempletList);
                        PluginMessage.notifyInfo("加载模板完成");
                    } else {
                        PluginMessage.notifyError("未能成功加载模板，模板为空或模板不符合要求");
                    }
                } catch (Exception ex) {
                    log.error("从本地加载模板异常", ex);
                    PluginMessage.notifyError("从本地加载模板异常");
                }
            }
        });
    }


    public static void setCodeTempletList(List<CodeTempletModel> codeTempletList) {
        UtilState.getInstance().clearCodeTempletList();
        UtilState.getInstance().setCodeTempletList(codeTempletList);
    }

    /**
     * 本地默认模板文件是否存在
     *
     * @return
     */
    public static Boolean loaclMdFileExists() {
        File localMdFile = new File(defaultLocalMdFilePath);
        return localMdFile.exists();
    }


    /**
     * 获得代码模板集合
     *
     * @param file
     * @return
     */
    public static List<CodeTempletModel> getCodeTempletList(File file) {
        try {
            List<CodeTempletModel> codeTempletList = new ArrayList<>();
            List<MarkdownTableModel> mdTableList = MarkDownUtil.getInstance().getMdTables(file);
            mdTableList.forEach(i -> {
                CodeTempletModel codeTemplet = new CodeTempletModel();
                List<String> rowData = i.getTitleRow();
                codeTemplet.setTitle(rowData.get(1));
                codeTemplet.setDescribe(rowData.get(2));
                codeTemplet.setTag(rowData.get(3));
                codeTemplet.setPeople(rowData.get(4));
                codeTemplet.setVersion(rowData.get(5));
                codeTemplet.setCodeType(i.getCodeBlockType());
                codeTemplet.setCodeTemplet(i.getCodeBlock());
                codeTempletList.add(codeTemplet);
            });
            return codeTempletList;
        } catch (Exception ex) {
            log.error("从文件中获取模板集合失败", ex);
        }
        return new ArrayList();
    }


}

