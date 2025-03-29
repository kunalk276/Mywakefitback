package com.wakefit.ecommerce.serviceimplement;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakefit.ecommerce.dto.ProductDTO;
import com.wakefit.ecommerce.entity.*;
import com.wakefit.ecommerce.exception.ResourceNotFoundException;
import com.wakefit.ecommerce.repository.CategoryRepository;
import com.wakefit.ecommerce.repository.RoleRepository;
import com.wakefit.ecommerce.repository.UserRepository;
import com.wakefit.ecommerce.service.ProductService;
import com.wakefit.ecommerce.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) { 
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        if (user.getCart() != null) {
            saveCart(user.getCart());
        }
        if (user.getOrders() != null) {
            for (Order order : user.getOrders()) {
                saveOrder(order);
            }
        }
        if (user.getAddresses() != null) {
            for (Address address : user.getAddresses()) {
                saveAddress(address);
            }
        }
        if (user.getFeedbacks() != null) {
            for (Feedback feedback : user.getFeedbacks()) {
                saveFeedback(feedback);
            }
        }

        return userRepository.save(user);
    }

    private void saveCart(Cart cart) {
    }

    private void saveOrder(Order order) {
        if (order.getCart() != null) {
            saveCart(order.getCart());
        }
        if (order.getProducts() != null) {
            List<ProductDTO> productDtos = order.getProducts().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

            for (ProductDTO productDto : productDtos) {
                productService.addProduct(productDto);
            }
        }
    }

    private void saveAddress(Address address) {
    }

    private void saveFeedback(Feedback feedback) {
        ProductDTO productDto = convertToDto(feedback.getProduct());
        if (productDto != null) {
            if (productDto.getProductId() == null) {
                productDto = productService.addProduct(productDto);
            } else {
                productDto = productService.updateProduct(productDto);
            }
            feedback.setProduct(convertToEntity(productDto));
        }
    }

    @Override
    public User updateUser(Long userId, User user) {
        Optional<User> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            existingUser.setUserName(user.getUserName());
            if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setMobNo(user.getMobNo());

            Set<Role> roles = user.getRoles();
            if (roles != null && !roles.isEmpty()) {
                existingUser.setRoles(roles);
            }

            return userRepository.save(existingUser);
        } else {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
    }

    @Override
    public User register(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByRole("customer")
                    .orElseThrow(() -> new RuntimeException("Error: Role 'customer' is not found."));
            user.setRoles(Set.of(defaultRole));
        }

        return saveUser(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with username " + userName);
        }
        return user;
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public long getUserCount() {
        return userRepository.count();
    }

    private ProductDTO convertToDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO productDto = new ProductDTO();
        productDto.setProductId(product.getProductId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImages(product.getImages());
        productDto.setStockQuantity(product.getStockQuantity());
        if (product.getCategory() != null) {
            productDto.setCategoryId(product.getCategory().getCategoryId());
            productDto.setCategoryName(product.getCategory().getName());
        }
        return productDto;
    }

    private Product convertToEntity(ProductDTO productDto) {
        if (productDto == null) {
            return null;
        }
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImages(productDto.getImages());
        product.setStockQuantity(productDto.getStockQuantity());
        if (productDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
            product.setCategory(category);
        }
        return product;
    }
}
