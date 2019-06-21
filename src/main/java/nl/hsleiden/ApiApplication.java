package nl.hsleiden;

import com.google.inject.Module;
import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import nl.hsleiden.model.User;
import nl.hsleiden.persistence.UserDAO;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peter van Vliet
 */
public class ApiApplication extends Application<RestApiConfig>
{
    private final Logger logger = LoggerFactory.getLogger(ApiApplication.class);
    
    private ConfiguredBundle assetsBundle;
    private GuiceBundle guiceBundle;
    private RestApiGuiceModule module;

    
    @Override
    public String getName()
    {
        return "webwinkelapi";
    }
    
    @Override
    public void initialize(Bootstrap bootstrap)
    {
        module =  new RestApiGuiceModule();
        guiceBundle = createGuiceBundle(RestApiConfig.class, module);

//        assetsBundle = (ConfiguredBundle) new ConfiguredAssetsBundle("/assets/", "/client", "index.html");
//        bootstrap.addBundle(assetsBundle);

        bootstrap.addBundle(guiceBundle);
    }
    
    @Override
    public void run(RestApiConfig configuration, Environment environment)
    {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin, Authorization");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
//        name = configuration.getApiName();
//
//        logger.info(String.format("Set API name to %s", name));
//
        setupAuthentication(environment);
//        configureClientFilter(environment);
    }
    
    private GuiceBundle createGuiceBundle(Class<RestApiConfig> configurationClass, Module module)
    {
        GuiceBundle.Builder guiceBuilder = GuiceBundle.<RestApiConfig>newBuilder()
                .addModule(module)
                .enableAutoConfig("nl.hsleiden")
                .setConfigClass(configurationClass);

        return guiceBuilder.build();
    }
    
    private void setupAuthentication(Environment environment)
    {
        try {
            UserDAO userDAO = guiceBundle.getInjector().getInstance(UserDAO.class);
            AuthenticationService authenticationService = new UnitOfWorkAwareProxyFactory(module.getHibernate()).create(AuthenticationService.class, UserDAO.class, userDAO);
            ApiUnauthorizedHandler unauthorizedHandler = guiceBundle.getInjector().getInstance(ApiUnauthorizedHandler.class);

            environment.jersey().register(new AuthDynamicFeature(
                    new BasicCredentialAuthFilter.Builder<User>()
                            .setAuthenticator(authenticationService)
                            .setAuthorizer(authenticationService)
                            .setRealm("SUPER SECRET STUFF")
                            .setUnauthorizedHandler(unauthorizedHandler)
                            .buildAuthFilter())
            );

            environment.jersey().register(RolesAllowedDynamicFeature.class);
            environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    
    private void configureClientFilter(Environment environment)
    {
        environment.getApplicationContext().addFilter(
            new FilterHolder(new ClientFilter()),
            "/*",
            EnumSet.allOf(DispatcherType.class)
        );
    }
    
    public static void main(String[] args) throws Exception
    {
        new ApiApplication().run(args);
    }
}
