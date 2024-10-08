package com.wakefit.ecommerce.serviceimplement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakefit.ecommerce.dto.ProductDTO;
import com.wakefit.ecommerce.entity.Address;
import com.wakefit.ecommerce.entity.Cart;
import com.wakefit.ecommerce.entity.Category;
import com.wakefit.ecommerce.entity.Feedback;
import com.wakefit.ecommerce.entity.Order;
import com.wakefit.ecommerce.entity.Product;
import com.wakefit.ecommerce.entity.User;
import com.wakefit.ecommerce.exception.ResourceNotFoundException;
import com.wakefit.ecommerce.repository.CategoryRepository;
import com.wakefit.ecommerce.repository.UserRepository;
import com.wakefit.ecommerce.service.ProductService;
import com.wakefit.ecommerce.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;  

    @Override
    public User saveUser(User user) {
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
        // Implement address save logic
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
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setMobNo(user.getMobNo());
            existingUser.setRole(user.getRole());

            return userRepository.save(existingUser);
        } else {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
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
