package dk.cngroup.lentils.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cypher")
public class CypherController
{
	private static final Logger LOGGER = LoggerFactory.getLogger(dk.cngroup.lentils.controller.CypherController.class);
}
