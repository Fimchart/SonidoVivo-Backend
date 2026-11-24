CREATE TABLE ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NULL,
    usuario_email VARCHAR(150) NOT NULL,
    usuario_nombre VARCHAR(150) NOT NULL,
    fecha DATETIME NOT NULL,
    total BIGINT NOT NULL,
    estado VARCHAR(40) NOT NULL DEFAULT 'COMPLETADA',
    metodo_pago VARCHAR(80) DEFAULT 'Webpay / Tarjeta',
    direccion_envio VARCHAR(255)
);

CREATE TABLE detalle_ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    venta_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    producto_codigo VARCHAR(50),
    producto_titulo VARCHAR(200) NOT NULL,
    precio_unitario BIGINT NOT NULL,
    cantidad INT NOT NULL,
    subtotal BIGINT NOT NULL,
    CONSTRAINT fk_detalle_venta
        FOREIGN KEY (venta_id) REFERENCES ventas(id)
        ON DELETE CASCADE
);