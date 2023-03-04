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

        admin.getServiceProviders().add(serviceProvider);
        adminRepository1.save(admin);
        return admin;

    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{
        for(CountryName countryName1 : CountryName.values()) {
            if(countryName1.name().equalsIgnoreCase(countryName)) {
                ServiceProvider serviceProvider = serviceProviderRepository1.findById(serviceProviderId).orElse(null);

                if(serviceProvider == null){
                    throw new NullPointerException();
                }

                Country country = new Country();
                country.setCountryName(countryName1);
                country.setCode(countryName1.toCode());
                country.setUser(null);
                country.setServiceProvider(serviceProvider);

                serviceProvider.getCountryList().add(country);

                serviceProviderRepository1.save(serviceProvider);

                return serviceProvider;
            }
        }

        throw new CountryNotFoundException("Country not found");
    }
    public static class CountryNotFoundException extends Exception {
        public CountryNotFoundException(String errorMessage) {
            super(errorMessage);
        }

        public CountryNotFoundException() {

        }
    }
}
