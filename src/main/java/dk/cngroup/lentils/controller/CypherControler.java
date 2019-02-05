package dk.cngroup.lentils.controller;

import dk.cngroup.lentils.entity.Cypher;
import dk.cngroup.lentils.service.CypherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cypher")
public class CypherControler
{
    private static final Logger logger = LoggerFactory.getLogger(dk.cngroup.lentils.controller.CypherControler.class);

    @Autowired
    private CypherService cypherService;

    @RequestMapping(value = "/one/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public Cypher getOne(@PathVariable Integer id){
        return null;
    }
}
