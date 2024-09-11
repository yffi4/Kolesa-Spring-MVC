package kz.kuleshov.springStart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ownerRepository ownerRepository;
    @Autowired
    private CountryRepository countryRepository;
    @GetMapping("/add-car")
    public String openAddCar(Model model){
        List<Owner> owners = ownerRepository.findAll();
        model.addAttribute("owners", owners);
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "add-car";
    }

    @PostMapping("/add-car")
    public String addCar(@RequestParam("model") String model,
                         @RequestParam("year") int year,
                         @RequestParam("price") int price,
                         @RequestParam("ownerId") Owner owner,
                         @RequestParam("countryId") List<Country> countries){
        Car car = new Car();
        car.setModel(model);
        car.setYear(year);
        car.setPrice(price);
        car.setOwner(owner);
        car.setCountries(countries);
        carRepository.save(car);
        return "redirect:home";
    }
    @GetMapping("/home")
    public String openHome(Model model,
                           @RequestParam(required = false, name = "search") String search,
                           @RequestParam(required = false, name = "sort") String sort,
                           @RequestParam(required = false, name = "sortByAge") String sortByAge){
        if (search != null){
            List<Car> cars = carRepository.findAllByModelContainsIgnoreCase(search);
            model.addAttribute("cars", cars);
        } else if (search ==null && "asc".equals(sort)) {
            List<Car> cars = carRepository.findAllByOrderByPriceAsc();
            model.addAttribute("cars", cars);
        } else if ("desc".equals(sort)) {
            List<Car> cars = carRepository.findAllByOrderByPriceDesc();
            model.addAttribute("cars", cars);
        } else if ("old".equals(sortByAge)) {
            List<Car> cars = carRepository.findAllByOrderByYearAsc();
            model.addAttribute("cars", cars);
        } else if ("young".equals(sortByAge)) {
            List<Car> cars = carRepository.findAllByOrderByYearDesc();
            model.addAttribute("cars", cars);
        } else{
            List<Car> cars = carRepository.findAll();
            model.addAttribute("cars", cars);
        }

        return "home";

    }
    @GetMapping("/details")
    public String details(@RequestParam("id") Long id, Model model){
        Car car = carRepository.findAllById(id);
        model.addAttribute("car", car);
        List<Owner> owners = ownerRepository.findAll();
        model.addAttribute("owners", owners);
        model.addAttribute("ownerId", car.getOwner().getId());
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);
        List<Country> nonSelectedCountries = new ArrayList<>();
        for (int i = 0; i < countries.size(); i++){
            boolean flag = false;
            for (int j = 0; i < car.getCountries().size(); j++){
                if (car.getCountries().get(j).getId() != countries.get(i).getId()){
                    flag = true;
                    break;
                }

            }
            if (flag){
                nonSelectedCountries.add(countries.get(i));
            }
        }
        model.addAttribute("nsc", nonSelectedCountries);
        return "details";
    }
    @PostMapping("/update-car")
    public String updateCar(@RequestParam("id") Long id,
                            @RequestParam("model") String model,
                            @RequestParam("year") int year,
                            @RequestParam("price") int price,
                            @RequestParam("ownerId") Owner owner,
                            @RequestParam("countryId") List<Country> countries){
        Car updCar = new Car();
        updCar.setId(id);
        updCar.setModel(model);
        updCar.setYear(year);
        updCar.setPrice(price);
        updCar.setOwner(owner);
        updCar.setCountries(countries);
        carRepository.save(updCar);
        return "redirect:home";
    }
    @PostMapping("/delete-car")
    public String deleteCar(@RequestParam("id") Long id){
        carRepository.deleteById(id);
        return "redirect:home";
    }
    @GetMapping("/login")
    public String openLogin(){
        return "login";
    }

}
