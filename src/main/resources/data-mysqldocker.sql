INSERT INTO categories (name, description) VALUES 
('Electrónica', 'Productos electrónicos como smartphones, laptops, etc.'),
('Ropa', 'Prendas de vestir para hombres, mujeres y niños.'),
('Libros', 'Libros de diversos géneros y autores.'),
('Hogar', 'Artículos para el hogar, decoración y muebles.'),
('Juguetes', 'Juguetes y juegos para niños de todas las edades.'),
('Deportes', 'Equipamiento y ropa deportiva.'),
('Belleza', 'Productos de belleza y cuidado personal.'),
('Alimentos', 'Productos alimenticios, snacks y bebidas.'),
('Mascotas', 'Artículos y alimentos para mascotas.'),
('Tecnología', 'Últimas novedades en tecnología y gadgets.');

INSERT INTO products (name, description, price, category_id)
VALUES ('Producto 1', 'Descripción del Producto 1', 10.99, 1),
       ('Producto 2', 'Descripción del Producto 2', 15.49, 2),
       ('Producto 3', 'Descripción del Producto 3', 7.99, 1);