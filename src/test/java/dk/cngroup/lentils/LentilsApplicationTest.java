package dk.cngroup.lentils;


import dk.cngroup.lentils.config.DataConfig;
import dk.cngroup.lentils.service.ObjectGenerator;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {LentilsApplication.class, ObjectGenerator.class})
public class LentilsApplicationTest
{
}