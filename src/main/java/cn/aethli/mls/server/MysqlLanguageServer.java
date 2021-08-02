package cn.aethli.mls.server;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;

import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;
import org.springframework.stereotype.Component;

import cn.aethli.mls.datasource.DataSourceManager;

@Component
public class MysqlLanguageServer implements LanguageServer {

    @Resource
    private DataSourceManager dataSourceManager;

    @Override
    public void exit() {
        // TODO Auto-generated method stub

    }

    @Override
    public TextDocumentService getTextDocumentService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        dataSourceManager.closeAll();
        return null;
    }

}
