<div id="top"></div>

[![](C:\Users\dell\Downloads\AWS-la-gi-Cac-dich-vu-noi-bat-AWS-cung.png)

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="C:\Users\dell\Downloads\logo.jpg" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Trường đại học sư phạm kĩ thuật TPHCM</h3>

  <p align="center">
     Cloud Computing
    <br />
    <a href="https://github.com/phongdz-cloud/lambda-student-java/blob/master/README.md"><strong>Explore the docs »</strong></a>
    <br />
    <br />
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#conclution">Conclution</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
![](https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/aboutProject.png?alt=media&token=06e29169-889e-41e2-92cb-4cf327f1057d)


Đầu tiên, em xin gửi lời cảm ơn chân thành đến Trường Đại học Sư Phạm Kỹ Thuật TPHCM đã đưa môn học Cloud Computing vào chương trình giảng dạy. Đặc biệt, em xin gửi lời cảm ơn sâu sắc đến giảng viên bộ môn - thầy Huỳnh Xuân Phụng đã dạy dỗ, truyền đạt những kiến thức quý báu cho em trong suốt thời gian học tập vừa qua. Trong thời gian tham gia lớp học Cloud Computing của thầy, em đã có thêm cho mình nhiều kiến thức về aws, tinh thần học tập hiệu quả, nghiêm túc. Nhờ có những sự góp ý từ thầy nhóm đã hoàn thành tốt phần bài cuối kì Cloud Computing. Một lần nữa em xin chân thành cảm ơn thầy 

Đồ án lần này nhóm thực hiện có tên là Quản lý sinh viên dùng dịch vụ Serverless đặc biệt là Lambda Function, DynamoDB, ApiGateway... Ngoài ra còn triển khai thêm các dịch vụ khác như S3, Redis cache. 



### Built With

Các công nghệ, ngôn ngữ, thư viện và các dịch vụ của AWS nhóm sử dụng.

- <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/cf/Angular_full_color_logo.svg/2048px-Angular_full_color_logo.svg.png" width=50 height=50> Angular là một framework mạnh mẽ để có thể xây dựng Front-end một cách nhanh chóng và hiệu quả <br>
- <img src="https://seeklogo.com/images/J/java-logo-7F8B35BAB3-seeklogo.com.png" width=50 height=50>  Java là một ngôn ngữ thuần hướng đối tượng
- <img src="https://cdn.svgporn.com/logos/aws.svg" width=30 height=30>  Các dịch vụ dùng cho project như Lambda Funtion, API Gateway, DynamoDB, S3, EC2<br>




<!-- GETTING STARTED -->
## Getting Started

Để có thế bắt đầu dự án thì các bạn phải làm theo các thư viện cũng như cài đặt các gói cần thiết để có thể chạy project của mình ở phía bên dưới

### Installation

 Để có thể khởi động được dự án Back-end các bạn phải cài đặt theo các yêu cầu phía bên dưới

1. Tất cả các API của nhóm viết  [https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/Lambda-Student.postman_collection.json?alt=media&token=52ad09c0-02d6-4cfa-b4f6-55b10f0c2885](https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/Lambda-Student.postman_collection.json?alt=media&token=52ad09c0-02d6-4cfa-b4f6-55b10f0c2885)
2. Clone the repo
   ```sh
   git clone https://github.com/phongdz-cloud/lambda-student-java
   ```
3. cài đặt NPM packages
   ```sh
   npm install -g @angular/cli
   ```
4. cài đặt Serverless Framework
   ```js
   npm install -g serverless
   ```
4. cài đặt NodeJS
   ```js
   https://nodejs.dev/download/
   ```
4. cài đặt Nodejs
   ```js
   https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html
   ```
4. Khởi tạo một dự án từ serverless framework
   ```js
   serverless create --template aws-java-gradle --path myService
   https://www.serverless.com/framework/docs/providers/aws/cli-reference/create
   ```



<!-- USAGE EXAMPLES -->
## Usage

Mình sẽ để lại những hình ảnh ở các bước cài đặt phía trên cũng như chạy thử phần dự án để các bạn có thể làm theo và chạy ra được kết quả như mình và các bạn nhớ lấy key và secret key cho AWS cấp cho mình nhé và các bạn nhớ xem kỹ phần cấu hình ở file serverless.yml của mình để có thể deploy các cấu hình lên AWS.

![](https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/installNPM.png?alt=media&token=69f201de-3b72-4e7d-abaa-cea339870825)
![](https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/angular.png?alt=media&token=9980ca04-087d-4e0a-99f6-e2a25b8d09e8)
![](https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/awsconfigure.png?alt=media&token=a998d3dd-f7a0-4e4a-bcbc-7aab69ffc6a8)
![](https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/serverlessversion.png?alt=media&token=11624d3d-86b2-4f00-9ead-3db47a106d47)
![](https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/serverless.png?alt=media&token=f5cee1a4-54da-4374-b14f-311c37efc4ae)
![](https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/keyAWS.png?alt=media&token=2a5522f8-04eb-4e2c-847c-47612a0d319a)
![](https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/deploy1.png?alt=media&token=dfd81e27-302f-4b22-8b7b-cb3f5169af85)
![](https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/deploy2.png?alt=media&token=dfd81e27-302f-4b22-8b7b-cb3f5169af85)
![](https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/deploy3.png?alt=media&token=dfd81e27-302f-4b22-8b7b-cb3f5169af85)
![](https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/deploy4.png?alt=media&token=dfd81e27-302f-4b22-8b7b-cb3f5169af85)
Đến bước này các bạn đãv có thể mở postman và import nội dung API ở trên và test các Lambda Function ở dự án này được rồi.





<!-- CONTRIBUTING -->
## Contributing

Những đóng góp của bạn sẽ giúp cho dự án này hoàn thiện và đầy đủ hơn. Vì vậy mọi đóng góp của bạn sẽ là sự động viên lớn cho mình cũng như là giúp dự án này sẽ tốt hơn.

Vì vậy nếu bạn có đề xuất gì để cho dự án này tốt hơn, bạn hãy tạo fork repo và clone dự án về máy của bạn.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- CONTACT -->
## Contact

Để có thể liên hệ với mình khi gặp khó khăn mình sẽ để thông tin tại đây- [https://www.facebook.com/hohoai.phong.98/](https://twitter.com/your_username) - 19110262@student.hcmute.edu.vn - https://www.facebook.com/profile.php?id=100029562436038 - 19110302@student.hcmute.edu.vn

Project Link: [https://github.com/phongdz-cloud/lambda-student-java](https://github.com/your_username/repo_name)





<!-- ACKNOWLEDGMENTS -->
## Conclusion

Cảm ơn các bạn đã xem tới tận đây nhòm mình chúc các bạn có thể cài đặt được dự án cũng như có thể áp dụng các kiến từ dự án của bọn mình vào dự án của bạn  và mình cũng rất vui nếu bạn góp ý cũng như đóng góp và xây dựng dự án này để có thể giúp ích cho các bạn khóa khác khi học môn này, và em cũng không quên cảm ơn thầy Huỳnh Xuân Phụng đã giúp nhóm em hoàn thành tốt dự án này.