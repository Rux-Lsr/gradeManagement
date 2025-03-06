package org.ruxlsr.generationficherecapitulative.services;

import java.util.List;

import org.ruxlsr.generationficherecapitulative.model.RecapModule;

public interface IRecapService {
    List<RecapModule> genererRecapModule(int moduleId);
}
