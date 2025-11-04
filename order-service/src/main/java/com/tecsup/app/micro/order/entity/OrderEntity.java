package com.tecsup.app.micro.order.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
//Creando la clase OrderEntity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", nullable = false)
    private String orderNumber;

    @Column(name = "user_id", nullable = false)
    private Long createdBy;

    @Column(nullable = false, length = 15)
    private String status;

    @Column(name="total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 👇 Relación uno a muchos, mappedBy = "order" → hace referencia al atributo en OrderItemEntity
    //cascade = CascadeType.ALL → guarda automáticamente los items cuando guardas la orden.
    //orphanRemoval = true → elimina los items si se quitan de la lista.
    //fetch = FetchType.LAZY → para no cargar items si no son necesarios.
    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemEntity> items = new ArrayList<>();
}
