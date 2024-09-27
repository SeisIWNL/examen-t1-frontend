package pe.edu.cibertec.VehiculoFront.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.VehiculoFront.dto.VehicleRequest;
import pe.edu.cibertec.VehiculoFront.dto.VehicleResponse;
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

    @PostMapping("/auth")
    public String autenticar(@RequestParam("placa") String placa, Model model) {
        if (placa == null || placa.trim().isEmpty()) {
            VehicleModel vehicleModel = new VehicleModel("101", "Por favor, Ingrese una placa", placa);
            model.addAttribute("vehicleModel", vehicleModel);
            return "inicio";
        }

        String rgx = "^[a-zA-Z]{3}-[0-9]{3}$";

        // Validar que la placa cumpla con la longitud y formato
        if (!placa.matches(rgx)) {
            VehicleModel vehicleModel = new VehicleModel("202", "Por favor, Ingrese una placa válida", placa);
            model.addAttribute("vehicleModel", vehicleModel);
            return "inicio";
        }

        String api = "http://localhost:8081/auth/consultar";
        VehicleRequest vehicleRequest = new VehicleRequest(placa);
        VehicleResponse vehicleResponse = restTemplate.postForObject(api, vehicleRequest, VehicleResponse.class);

        if (vehicleResponse != null && "ASR-121".equals(vehicleResponse.placa()) || "FAD-124".equals(vehicleResponse.placa())) {
            model.addAttribute("placa", vehicleResponse.placa());
            model.addAttribute("marca", vehicleResponse.marca());
            model.addAttribute("modelo", vehicleResponse.modelo());
            model.addAttribute("nro_asientos", vehicleResponse.nro_asientos());
            model.addAttribute("precio", vehicleResponse.precio());
            model.addAttribute("color", vehicleResponse.color());

            return "vehiculo";
        } else  {
            VehicleModel vehicleModel = new VehicleModel("303", "Vehiculo no registrado, intente otra placa", placa);
            model.addAttribute("vehicleModel", vehicleModel);
            return "inicio";
        }
    }
}
