package nki.ClimCue;

import org.apache.catalina.Context;
import org.apache.catalina.session.PersistentManager;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

public class TomcatConfig {
    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                PersistentManager manager = new PersistentManager();
                manager.setSaveOnRestart(false);
                context.setManager(manager);
            }
        };
    }
}
