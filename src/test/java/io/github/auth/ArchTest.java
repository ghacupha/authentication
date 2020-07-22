package io.github.auth;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("io.github.auth");

        noClasses()
            .that()
                .resideInAnyPackage("io.github.auth.service..")
            .or()
                .resideInAnyPackage("io.github.auth.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..io.github.auth.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
