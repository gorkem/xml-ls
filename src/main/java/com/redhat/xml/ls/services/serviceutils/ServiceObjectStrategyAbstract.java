package com.redhat.xml.ls.services.serviceutils;

import java.util.List;

import com.redhat.xml.ls.parser.XMLNodes.XMLNode;

/**
 * ListCreatorStrategyInterface
 */
public abstract class ServiceObjectStrategyAbstract {

  public abstract void create(XMLNode node);

  public abstract List<?> getNodeList();
}