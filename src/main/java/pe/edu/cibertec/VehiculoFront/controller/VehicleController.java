package pe.edu.cibertec.VehiculoFront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.VehiculoFront.viewmodel.VehicleModel;

@Controller
@RequestMapping("/vehicle")
public class VehicleController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/inicio")
    public String inicio(Model model) {
        VehicleModel vehicleModel = new VehicleModel("000","",null);
        model.addAttribute("vehicleModel", vehicleModel);
        return "inicio";
    }
}
