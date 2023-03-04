package com.driver.services.impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Autowired
    UserRepository userRepository;

    @Override
    public Admin register(String username, String password) {

        Admin admin = new Admin();
        admin.setPassword(username);
        admin.setPassword(password);

        adminRepository1.save(admin);
        return admin;

    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {
        Admin admin = adminRepository1.findById(adminId).get();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(providerName);
        serviceProvider.setAdmin(admin);

        admin.getServiceProviderList().add(serviceProvider);
        adminRepository1.save(admin);
        return admin;

    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{
        if(countryName.equals("IND") || countryName.equals("USA") || countryName.equals("AUS") || countryName.equals("CHI") || countryName.equals("JPN")){

        Country country = new Country();
        ServiceProvider serviceProvider = serviceProviderRepository1.findById(serviceProviderId).get();

        if(countryName.equals("IND")){
            country.setCountryName(CountryName.IND);
            country.setCode(CountryName.IND.toCode());
        }
        else if(countryName.equals("USA")){
            country.setCountryName(CountryName.USA);
            country.setCode(CountryName.USA.toCode());
        }
        else if(countryName.equals("AUS")){
            country.setCode(CountryName.AUS.toCode());
            country.setCountryName(CountryName.AUS);
        }
        else if(countryName.equals("CHI")){
            country.setCode(CountryName.CHI.toCode());
            country.setCountryName(CountryName.CHI);
        }
        else if(countryName.equals("JPN")){
            country.setCode(CountryName.JPN.toCode());
            country.setCountryName(CountryName.JPN);
        }

          country.setServiceProvider(serviceProvider);
          serviceProvider.getCountryList().add(country);
          serviceProviderRepository1.save(serviceProvider);
          return serviceProvider;
        }
        else{
            throw new Exception("Country Is Not Found");
        }
    }
}
