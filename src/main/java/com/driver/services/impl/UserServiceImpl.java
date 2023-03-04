package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception{

        User user = new User();
        Country country = new Country();

        if(countryName.equals("IND") || countryName.equals("USA") || countryName.equals("AUS") || countryName.equals("CHI") || countryName.equals("JPN")) {

            user.setUsername(username);
            user.setPassword(password);


            if (countryName.equals("IND")) {
                country.setCountryName(CountryName.IND);
                country.setCode(CountryName.IND.toCode());
            } else if (countryName.equals("USA")) {
                country.setCountryName(CountryName.USA);
                country.setCode(CountryName.USA.toCode());
            } else if (countryName.equals("AUS")) {
                country.setCode(CountryName.AUS.toCode());
                country.setCountryName(CountryName.AUS);
            } else if (countryName.equals("CHI")) {
                country.setCode(CountryName.CHI.toCode());
                country.setCountryName(CountryName.CHI);
            } else if (countryName.equals("JPN")) {
                country.setCode(CountryName.JPN.toCode());
                country.setCountryName(CountryName.JPN);
            }

            country.setUser(user);
            user.setOriginalCountry(country);
            user.setConnected(false);

            int id = userRepository3.save(user).getId();
            user.setOriginalIp(country.getCode() + "." + id);

            userRepository3.save(user);
        }
        else{
            throw new Exception("Country Is Not Found");
        }
        return user;
    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {

      User user = userRepository3.findById(userId).get();
      ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();

      serviceProvider.getUsers().add(user);
      user.getServiceProviderList().add(serviceProvider);

      serviceProviderRepository3.save(serviceProvider);
      return user;
    }
}
