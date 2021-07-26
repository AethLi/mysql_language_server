package cn.aethli.mls;

import org.eclipse.lsp4j.services.LanguageServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.aethli.mls.server.MysqlLanguageServer;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class MlsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlsApplication.class, args);
		LanguageServer server = new MysqlLanguageServer();
        // Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server,
        // inputstream, outputstream);
	}

}
