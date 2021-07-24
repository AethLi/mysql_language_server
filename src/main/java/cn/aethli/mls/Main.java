package cn.aethli.mls;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;

public class Main {
    public static void main(String[] args) {
        LanguageServer server = new MysqlLanguageServer();
        // Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, inputstream, outputstream);
    }
}
