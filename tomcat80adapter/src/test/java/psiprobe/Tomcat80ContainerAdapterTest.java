/**
 * Licensed under the GPL License. You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE.
 */
package psiprobe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.catalina.Context;
import org.apache.catalina.Valve;
import org.apache.jasper.JspCompilationContext;
import org.apache.tomcat.util.descriptor.web.ApplicationParameter;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.junit.Assert;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;
import psiprobe.model.ApplicationResource;

/**
 * The Class Tomcat80ContainerAdapterTest.
 */
public class Tomcat80ContainerAdapterTest {

  /** The context. */
  @Mocked
  Context context;

  /**
   * Creates the valve.
   */
  @Test
  public void createValve() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    Valve valve = adapter.createValve();
    assertEquals("psiprobe.Tomcat80AgentValve[Container is null]", valve.toString());
  }

  /**
   * Can bound to null.
   */
  @Test
  public void canBoundToNull() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    assertFalse(adapter.canBoundTo(null));
  }

  /**
   * Can bound to tomcat7.
   */
  @Test
  public void canBoundToTomcat7() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    assertTrue(adapter.canBoundTo("Apache Tomcat/8.0"));
  }

  /**
   * Can bound to tom ee.
   */
  @Test
  public void canBoundToTomEE() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    assertFalse(adapter.canBoundTo("Apache Tomcat (TomEE)/8.0"));
  }

  /**
   * Can bound to pivotal7.
   */
  @Test
  public void canBoundToPivotal7() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    assertFalse(adapter.canBoundTo("Pivotal tc..../7.0"));
  }

  /**
   * Can bound to pivotal8.
   */
  @Test
  public void canBoundToPivotal8() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    assertTrue(adapter.canBoundTo("Pivotal tc..../8.0"));
  }

  /**
   * Can bound to other.
   */
  @Test
  public void canBoundToOther() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    assertFalse(adapter.canBoundTo("Other"));
  }

  /**
   * Filter mappings.
   */
  @Test
  public void filterMappings() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    FilterMap map = new FilterMap();
    map.addServletName("psi-probe");
    map.addURLPattern("/psi-probe");
    assertEquals(2, adapter.getFilterMappings(map, "dispatcherMap", "filterClass").size());
  }

  /**
   * Creates the jsp compilation context.
   */
  @Test
  public void createJspCompilationContext() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    JspCompilationContext context = adapter.createJspCompilationContext("name", null, null, null,
        ClassLoader.getSystemClassLoader());
    assertEquals("org.apache.jsp.name", context.getFQCN());
  }

  /**
   * Adds the context resource link.
   */
  @Test
  public void addContextResourceLink() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    adapter.addContextResourceLink(context, new ArrayList<ApplicationResource>(), false);
  }

  /**
   * Adds the context resource.
   */
  @Test
  public void addContextResource() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    adapter.addContextResource(context, new ArrayList<ApplicationResource>(), false);
  }

  /**
   * Gets the application filter maps.
   */
  @Test
  public void applicationFilterMaps() {
    Assert.assertNotNull(new Expectations() {
      {
        context.findFilterMaps();
        result = new FilterMap();
      }
    });

    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    assertEquals(0, adapter.getApplicationFilterMaps(context).size());
  }

  /**
   * Application filters.
   */
  @Test
  public void applicationFilters() {
    Assert.assertNotNull(new Expectations() {
      {
        context.findFilterDefs();
        result = new FilterDef();
      }
    });

    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    assertEquals(1, adapter.getApplicationFilters(context).size());
  }

  /**
   * Application init params.
   */
  @Test
  public void applicationInitParams() {
    Assert.assertNotNull(new Expectations() {
      {
        context.findApplicationParameters();
        result = new ApplicationParameter();
      }
    });
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    assertEquals(0, adapter.getApplicationInitParams(context).size());
  }

  /**
   * Resource exists.
   */
  @Test
  public void resourceExists() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    assertTrue(adapter.resourceExists("name", context));
  }


  /**
   * Resource stream.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void resourceStream() throws IOException {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    adapter.getResourceStream("name", context);
  }

  /**
   * Resource attributes.
   */
  @Test
  public void resourceAttributes() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    adapter.getResourceAttributes("name", context);
  }

  /**
   * Gets the naming token.
   */
  @Test
  public void getNamingToken() {
    final Tomcat80ContainerAdapter adapter = new Tomcat80ContainerAdapter();
    assertNull(adapter.getNamingToken(context));
  }

}
