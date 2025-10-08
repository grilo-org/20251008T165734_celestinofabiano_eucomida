package com.geosapiens.eucomida.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BaseEntityTest {

    private TestEntity entity1;
    private TestEntity entity2;
    private TestEntity entity3;

    @BeforeEach
    void setUp() {
        entity1 = new TestEntity();
        entity2 = new TestEntity();
        entity3 = new TestEntity();
    }

    @Test
    void shouldGenerateUniqueId() {
        assertThat(entity1.getId()).isNull();
        assertThat(entity2.getId()).isNull();
    }

    @Test
    void shouldSetCreatedAtAutomatically() {
        entity1.setCreatedAt(LocalDateTime.now());
        assertThat(entity1.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldUpdateUpdatedAtOnPreUpdate() {
        LocalDateTime beforeUpdate = LocalDateTime.now().minusMinutes(1);
        entity1.setUpdatedAt(beforeUpdate);
        entity1.onUpdate();
        assertThat(entity1.getUpdatedAt()).isAfter(beforeUpdate);
    }

    @Test
    void shouldValidateEqualsForSameId() {
        UUID id = UUID.randomUUID();
        entity1.setId(id);
        entity2.setId(id);

        assertThat(entity1).isEqualTo(entity2);
        assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
    }

    @Test
    void shouldNotBeEqualIfIdsAreDifferent() {
        entity1.setId(UUID.randomUUID());
        entity2.setId(UUID.randomUUID());

        assertThat(entity1).isNotEqualTo(entity2);
    }

    @Test
    void shouldNotBeEqualIfIdIsNull() {
        entity1.setId(null);
        entity2.setId(UUID.randomUUID());

        assertThat(entity1).isNotEqualTo(entity2);
    }

    @Test
    void shouldNotBeEqualIfOtherObjectIsNull() {
        entity1.setId(UUID.randomUUID());

        assertThat(entity1).isNotEqualTo(null);
    }

    @Test
    void shouldNotBeEqualIfClassesAreDifferent() {
        entity1.setId(UUID.randomUUID());
        Object other = new Object();

        assertThat(entity1).isNotEqualTo(other);
    }

    @Test
    void shouldBeEqualToItself() {
        entity1.setId(UUID.randomUUID());

        assertThat(entity1).isEqualTo(entity1);
    }

    @Test
    void shouldHaveConsistentHashCode() {
        UUID id = UUID.randomUUID();
        entity1.setId(id);
        entity2.setId(id);

        assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
    }

    @Test
    void shouldHaveDifferentHashCodesForDifferentIds() {
        entity1.setId(UUID.randomUUID());
        entity2.setId(UUID.randomUUID());

        assertThat(entity1.hashCode()).isNotEqualTo(entity2.hashCode());
    }

    @Test
    void shouldHaveSameHashCodeForSameClassAndNullId() {
        entity1.setId(null);
        entity2.setId(null);

        assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
    }

    private static class TestEntity extends BaseEntity {
    }
}
