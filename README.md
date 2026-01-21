# proiect-dam-erasmus

Aplicația permite colaborarea între echipele de coordonare Erasmus, participanți și personalul administrativ, oferind posibilitatea de a gestiona resursele umane, financiare și logistice ale proiectelor, de a urmări progresul mobilităților, bugetul și termenele, precum și de a centraliza documentele și rapoartele asociate fiecărui proiect.

--- 11 Nov

- Adăugat UML nou.

- Implementat toate cele 16 entități JPA (domain/model) cu Lombok (@Data).

- Implementat toate cele 12 Repository-uri (domain/repository).

- Implementat scheletul logic pentru toate Serviciile (service) conform SPRINT 1+2.

- Adăugat ErasmusApplication (starter), commons/enums și configurare H2 pentru a rula proiectul.

- Rezolvat erorile de runtime JPA (ex: Project ID, Tranche value).

- Creat primul DTO (ApplicationRequestDTO) și refactorizat MobilityController și MobilityService pentru a-l folosi.
