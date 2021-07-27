package cn.aethli.mls.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseConfig {
    private String host;
    private String port;
    private String user;
    private String password;
}
