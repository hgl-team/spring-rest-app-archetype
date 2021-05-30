#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SinglePageFallbackController {
    private static final Logger log = LoggerFactory.getLogger(SinglePageFallbackController.class);

    @GetMapping(value = "/ui/**/{path:[^\\.]*}")
    public String forward(HttpServletRequest request) {
        log.debug("Forwarding to single page application");
        return "forward:/ui/index.html";
    }
}
