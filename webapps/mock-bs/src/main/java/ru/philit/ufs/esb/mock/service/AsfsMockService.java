package ru.philit.ufs.esb.mock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.philit.ufs.esb.MessageProcessor;
import ru.philit.ufs.esb.mock.client.EsbClient;
import ru.philit.ufs.model.cache.MockCache;
import ru.philit.ufs.model.converter.esb.JaxbConverter;
import ru.philit.ufs.model.entity.esb.eks.HeaderInfoType;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

public class AsfsMockService extends CommonMockService implements MessageProcessor {


    @Override
    public boolean processMessage(String message) {
        return false;
    }
}
