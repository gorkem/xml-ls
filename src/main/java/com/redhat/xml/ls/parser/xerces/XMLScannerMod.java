package com.redhat.xml.ls.parser.xerces;

import org.apache.xerces.impl.XMLScanner;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;

/**
 * XMLScannerMod
 */
public class XMLScannerMod extends XMLScanner {

  @Override
  public Boolean getFeatureDefault(String arg0) {
    return null;
  }

  @Override
  public Object getPropertyDefault(String arg0) {
    return null;
  }

  @Override
  public String[] getRecognizedFeatures() {
    return null;
  }

  @Override
  public String[] getRecognizedProperties() {
    return null;
  }

  @Override
  public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {
    super.reset(componentManager);
  }

}