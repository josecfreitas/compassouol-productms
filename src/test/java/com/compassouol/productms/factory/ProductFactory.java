package com.compassouol.productms.factory;

import com.compassouol.productms.model.Product;

import java.math.BigDecimal;

public class ProductFactory {

    public static Product createGenericProduct() {
        String BIG_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis justo pretium rhoncus cursus. Curabitur et felis porta, imperdiet nulla non, congue sapien. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Sed nec malesuada neque. Maecenas feugiat libero id fringilla iaculis. Sed id convallis leo, ut finibus lorem. Nulla faucibus non nisl eget ullamcorper. Duis interdum laoreet ipsum et dignissim. Nulla ultricies ipsum ut est dictum, vel volutpat felis varius. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Nunc tincidunt volutpat nisl fermentum vestibulum. Maecenas pretium in justo a ultrices. Etiam mattis luctus nulla, ac lobortis ex tempor eget. Maecenas mattis nulla erat, et aliquam nulla mollis viverra. Nam blandit, neque vitae sodales tristique, ipsum arcu dictum leo, vel varius ipsum mauris dignissim ipsum. Morbi diam neque, viverra sed ultricies non, euismod non dui. Fusce sit amet velit ultricies, iaculis arcu id, pellentesque ipsum. Donec ac tincidunt diam. Cras nisl ipsum, mollis non posuere id, tristique non justo. Nullam et laoreet elit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque auctor diam quis velit molestie, in viverra diam mattis. Cras orci libero, molestie eu eleifend sed, auctor eu magna. Donec aliquam sapien sed risus tempor, in tempus erat ultrices. Quisque vitae rutrum nibh. Suspendisse euismod gravida dignissim. Nulla et dolor blandit nunc malesuada ultricies. Sed finibus augue id eleifend molestie. Morbi ut nulla libero. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris non libero hendrerit nisl tempus venenatis. Maecenas ut dignissim ipsum, eget pharetra est. Etiam condimentum pharetra diam sed pharetra. Quisque tempus condimentum nisl vitae blandit. Integer nec erat vel nibh pulvinar finibus. Nulla facilisi. Sed id sem quis eros egestas rhoncus. Fusce dignissim suscipit accumsan. Sed tortor neque, facilisis maximus euismod et, ultricies sed mi. Pellentesque et erat auctor, sodales nisi ut, eleifend lacus. Pellentesque condimentum et orci eget eleifend. Integer dolor massa, commodo vitae tempus eget, lacinia non nulla. Fusce eu imperdiet neque. Donec sem eros, iaculis vel quam sit amet, fermentum porta diam. Donec metus libero, interdum et quam quis, fermentum interdum ex. Etiam sit amet tellus bibendum nunc viverra lobortis at ut nibh. Suspendisse pretium leo libero, ut sagittis ipsum tempus a. Aenean porta tortor a velit tristique suscipit. Donec malesuada gravida volutpat. Mauris varius ex quam, in dignissim felis condimentum quis.";

        Product product = new Product();
        product.setName("Test Product");
        product.setDescription(BIG_DESCRIPTION);
        product.setPrice(new BigDecimal("1000000000.123123"));

        return product;
    }
}
