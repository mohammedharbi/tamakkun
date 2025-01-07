# Tamakkun
<img width="256" alt="FinalProject logo-Tamakun" src="https://github.com/user-attachments/assets/64257a71-e45e-4396-8419-a2875cac1f57" />



## Introduction :
تُعد "تمكّن" منصة شاملة ومبتكرة تُعنى بالأطفال ذوي الإحتياجات الخاصة، حيث تفتح لهم آفاقًا جديدة للمشاركة في الأنشطة الترفيهية واللعب مثل أقرانهم. توفر المنصة جسراً للتواصل بين مراكز الألعاب وأولياء الأمور، مما يسهل عملية حجز المراكز بكل سلاسة. تلتزم كل مركز مشارك بتعيين أخصائي متخصص يتناسب مع احتياجات الطفل، لضمان تجربة آمنة وممتعة.

وليس هذا فحسب، بل تُعزز "تمكّن" من روح المجتمع الداعم، حيث يُتاح للأهالي فرصة تبادل الخبرات والنصائح والتشجيع، ليشكلوا معاً شبكة من الفهم والتضامن. عبر "تمكّن"، يمكن للأطفال ذوي الإحتياجات الخاصة الاستمتاع بلحظات لعب ذات معنى، بينما يجد الآباء مجتمعاً داعماً يتقاسمون معه الأهداف والاهتمامات.



Tamakkun is an inclusive platform dedicated to children with disabilities, providing them with opportunities to engage in play and recreation just like their peers. The system connects game centers with parents, allowing seamless booking of centers. Each participating center assigns a specialized specialist tailored to the child's specific disability, ensuring a safe and enjoyable experience. Additionally, Tamakkun fosters a supportive community where parents can share experiences, advice, and encouragement, building a network of understanding and solidarity. Special features include the generation of QR codes upon booking a center, sent to the parent's email, audio support, and an automatic request for reviews after visiting the center. The system is built using Spring Boot, DataGrip, SQL Server, Postman, Figma, and XAMPP.

## Models:
#### 1- Booking Model.
#### 2- BookingDate Model.
#### 3- Review Model.
#### 4- MyUser Model.




## My Endpoints :


#### 1- newBooking(Integer parent_id, Integer child_id, Integer centre_id, BookingDTO_In bookingDTOIn).
#### 2- newReview(Integer parent_id, Integer booking_id, Review review).
#### 3- getMyChildren(Integer user_id).
#### 4- getAllOldBookingsByCentre(Integer centre_id).
#### 5- getAllNewBookingsByCentre(Integer centre_id).
#### 6- getAllOldBookingsByParent(Integer parent_id).
#### 7- getAllNewBookingsByParent(Integer parent_id).
#### 8- getAllUnReviewedBookingsByParent(Integer parent_id).
#### 9- getAllReviewedBookingsByParent(Integer parent_id).
#### 10- unActiveParent(Integer parent_id).
#### 11- getAllUnActiveParent().
#### 12- updateBookingStatus().
#### 13- autoRequestForReviewAfterVisit().
#### 14- recommendationToParentsByCentresAndActivities(Integer parentId).
#### 15- cancelBooking(Integer parent_id, Integer booking_id).
#### 16- getCommentsByTicket(Integer user_id ,Integer ticket_id).
#### 17- getAllTicketByCenter (Integer center_id).


## Test :


#### 1- AuthRepositoryTest.
#### 2- CommunityRepositoryTest.
#### 3- SpecialistRepositoryTest.
#### 4- TicketCommentRepositoryTest.
#### 5- TicketRepositoryTest.

## Use Case Diagram :
<img width="381" alt="UseCase" src="https://github.com/user-attachments/assets/73031e0e-8925-40b4-a560-9f7ed92969e4" />


## Class Diagram : 
![Class Diagram](https://github.com/user-attachments/assets/31a49ce1-9c4a-4809-9051-b029d7c5fe23)

## Figma :
https://www.figma.com/design/SfnHrBfcihbW2VbvwtBlXu/Tamakkun?node-id=0-1&t=gDxYqcfseL1evoc8-1


## Presentation :
[Tamakkun Presentation.pdf](https://github.com/user-attachments/files/18326449/Tamakkun.Presentation.pdf)


## Postman API Doucumentation : 
[https://documenter.getpostman.com/view/29329177/2sA3JKc2NP](https://documenter.getpostman.com/view/39710002/2sAYJAcwb6)

