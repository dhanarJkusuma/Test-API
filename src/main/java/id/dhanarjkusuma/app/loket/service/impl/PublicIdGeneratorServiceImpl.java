package id.dhanarjkusuma.app.loket.service.impl;

import id.dhanarjkusuma.app.loket.service.PublicIdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PublicIdGeneratorServiceImpl implements PublicIdGeneratorService {

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public PublicIdGeneratorServiceImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private Long getNextSequence(){
        EntityManager em = entityManagerFactory.createEntityManager();
        try{
            Query q = em.createNativeQuery("SELECT NEXTVAL('service_order_transaction_public_id')");
            BigInteger value = (BigInteger) q.getSingleResult();
            return value.longValue();
        }finally {
            em.close();
        }
    }

    @Override
    public String generateTransactionPublicId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        Long sequenceId = getNextSequence();
        return String.format("%s0%s0%s", "INV", date, String.format("%5d", sequenceId));
    }
}
