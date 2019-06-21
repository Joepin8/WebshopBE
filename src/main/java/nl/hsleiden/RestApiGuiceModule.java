package nl.hsleiden;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;

public class RestApiGuiceModule extends AbstractModule {

    private final HibernateBundle<RestApiConfig> hibernate = new ScanningHibernateBundle<RestApiConfig>("nl.hsleiden") {
        @Override
        public DataSourceFactory getDataSourceFactory(RestApiConfig configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Provides
    public SessionFactory provideSessionFactory(RestApiConfig configuration, Environment environment) {
        SessionFactory sf = hibernate.getSessionFactory();
        if (sf == null) {
            try {
                hibernate.run(configuration, environment);
                return hibernate.getSessionFactory();
            } catch (Exception e) {
                System.out.println("Unable to run hibernatebundle");
                return null;
            }
        } else {
            return sf;
        }
    }

    @Override
    protected void configure() {

    }

    public HibernateBundle<RestApiConfig> getHibernate() {
        System.out.println("dit is hybernate"  + hibernate);
        return hibernate;
    }
}
