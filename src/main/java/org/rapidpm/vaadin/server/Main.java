package org.rapidpm.vaadin.server;


import static io.undertow.Handlers.redirect;
import static io.undertow.servlet.Servlets.servlet;
import static javax.servlet.DispatcherType.ERROR;
import static javax.servlet.DispatcherType.FORWARD;
import static javax.servlet.DispatcherType.INCLUDE;
import static javax.servlet.DispatcherType.REQUEST;

import java.util.Optional;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.servlet.ShiroFilter;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.ListenerInfo;

/**
 *
 */
public class Main {

  public static final String CONTEXT_PATH = "/";
  public static final String SHIRO_FILTER = "ShiroFilter";
  public static final String SHIRO_FILTER_MAPPING = "/*";

  public static void start() {
    main(new String[0]);
  }

  public static void shutdown() {
    undertowOptional.ifPresent(Undertow::stop);
  }

  private static Optional<Undertow> undertowOptional;

  public static void main(String[] args) {
    DeploymentInfo servletBuilder
        = Servlets.deployment()
                  .setClassLoader(Main.class.getClassLoader())
                  .setContextPath(CONTEXT_PATH)
                  .setDeploymentName("ROOT.war")
                  .setDefaultEncoding("UTF-8")
                  .addListener(new ListenerInfo(EnvironmentLoaderListener.class))
                  .addFilter(new FilterInfo(SHIRO_FILTER, ShiroFilter.class))
                  .addFilterUrlMapping(SHIRO_FILTER, SHIRO_FILTER_MAPPING, REQUEST)
                  .addFilterUrlMapping(SHIRO_FILTER, SHIRO_FILTER_MAPPING, FORWARD)
                  .addFilterUrlMapping(SHIRO_FILTER, SHIRO_FILTER_MAPPING, INCLUDE)
                  .addFilterUrlMapping(SHIRO_FILTER, SHIRO_FILTER_MAPPING, ERROR)
                  .addServlets(
                      servlet(
                          MainServlet.class.getSimpleName(),
                          MainServlet.class).addMapping(SHIRO_FILTER_MAPPING)
                  );

    DeploymentManager manager = Servlets
        .defaultContainer()
        .addDeployment(servletBuilder);

    manager.deploy();

    try {
      HttpHandler httpHandler = manager.start();
      PathHandler path = Handlers.path(redirect(CONTEXT_PATH))
                                 .addPrefixPath(CONTEXT_PATH, httpHandler);

      Undertow undertowServer = Undertow.builder()
                                        .addHttpListener(8080, "0.0.0.0")
                                        .setHandler(path)
                                        .build();
      undertowServer.start();

      undertowOptional = Optional.of(undertowServer);

      undertowServer.getListenerInfo().forEach(System.out::println);

    } catch (ServletException e) {
      e.printStackTrace();
      undertowOptional = Optional.empty();
    }
  }
}
