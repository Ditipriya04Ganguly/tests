package com.optum;


import com.optum.model.Patient;
import com.optum.model.PatientDisease;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientTester {
    static Map<Integer,Patientd> map= new HashMap<>();

    static  {
        //Variable for Timeframe
        LocalDate validDate = LocalDate.of(2022, 06, 30);
        //Variable for DOB
        LocalDate DOB = LocalDate.of(2000, 9, 04);

        map.put(1,new Patientd(Arrays.asList(
                new Patient(1,"R13.1", "Active", validDate, DOB)),
                new MapCreator(Arrays.asList(new Pair(1, "Dysphagia"))),
                new MapCreator(Arrays.asList(new Pair(1)))));

        map.put(2,new Patientd(Arrays.asList(
                new Patient(2,"R63.4", "Active", validDate, DOB)),
                new MapCreator(Arrays.asList(new Pair(2, "Weight Loss"))),
                new MapCreator(Arrays.asList(new Pair(2)))));

        map.put(3,new Patientd(Arrays.asList(
                new Patient(3,"R68.81", "Active", validDate, DOB)),
                new MapCreator(Arrays.asList(new Pair(3, "Early Satiety"))),
                new MapCreator(Arrays.asList(new Pair(3)))));

        map.put(5,new Patientd(Arrays.asList(
                new Patient(5,"K21.0", "Inactive", validDate, DOB)),
                new MapCreator(new ArrayList<>()),
                new MapCreator(Arrays.asList(new Pair(4)))));
        map.put(6,new Patientd(Arrays.asList(
                new Patient(6,"R68.81", "Active", validDate, DOB)),
                new MapCreator(Arrays.asList(new Pair(3, "Early Satiety"))),
                new MapCreator(Arrays.asList(new Pair(3)))));
        map.put(7,new Patientd(Arrays.asList(
                new Patient(7,"R68.81", "Active", validDate, DOB)),
                new MapCreator(Arrays.asList(new Pair(3, "Early Satiety"))),
                new MapCreator(Arrays.asList(new Pair(3)))));
        map.put(8,new Patientd(Arrays.asList(
                new Patient(8,"R68.81", "Active", validDate, DOB)),
                new MapCreator(Arrays.asList(new Pair(3, "Early Satiety"))),
                new MapCreator(Arrays.asList(new Pair(3)))));
        map.put(9,new Patientd(Arrays.asList(
                new Patient(9,"R68.81", "Active", validDate, DOB)),
                new MapCreator(Arrays.asList(new Pair(3, "Early Satiety"))),
                new MapCreator(Arrays.asList(new Pair(3)))));

    }

    @Test
    public void testForPatientDisease() {
        PatientTester pt= new PatientTester();
        PatientDisease patientDisease = new PatientDisease();
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer().newKieSession("diseaserules");
        for (int tid : map.keySet()) {
            for(Patient p:map.get(tid).pt) {
                kieSession.insert(p);
            }
        }
        kieSession.insert(LocalDate.now());
        kieSession.insert(patientDisease);
        kieSession.fireAllRules();
        kieSession.dispose();
        for(int tid:map.keySet())
        {
            pt.validatorDisease(patientDisease, map.get(tid).dismp, map.get(tid).riskmp);
        }

    }

    public void validatorDisease(PatientDisease pd,MapCreator disease, MapCreator risk){

                  for(int pid:disease.map.keySet())
                  {
                      if (pd.getPatient(pid)==null && disease.map.get(pid)==null){
                          assert (disease.map.get(pid));
                      }
                      assertEquals (pd.getMaplist().get(pd.getPatient(pid)),(disease.map.get(pid)));

                  }

                  for(int pid:risk.map.keySet())
                  {
                       assertEquals (pd.getPatient(pid).getRisk(),(risk.map.get(pid)));

                  }
    }
}
