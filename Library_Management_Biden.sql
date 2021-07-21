/*
~~NHỮNG PHẦN NÀO T VỪA LÀM SẼ CẬP NHẬT LÊN ĐÂY~~
NEW
	- Điều chỉnh sp_UpdateCost để mỗi khi thanh toán thì tự động thêm vào REVENUE luôn
	- Create PROC sp_DeleteBOOK thực hiện việc xoá sách (Thực ra nó không xoá mà chỉ đánh dấu để k hiển thị lên nữa thôi)
	- Create PROC sp_UndeletedBOOK thực hiện việc hoàn tác sách đã xoá
	- Update cost khi Reader thực hiện trả sách
	- Create VIEW view_BORROW_NOT_RETURN (Sách đang được mượn)
	- Create VIEW view_MOST_BORROWING (Sách mượn nhiều nhất, top 10)
	- Create TRIGGER trg_UpdateStockInBook_Status để update lại số lượng sách còn trong kho
OLD	
	- Insert BOOK_STATUS
	- Create VIEW cho BOOK
	- Create VIEW cho BOOK_STATUS
	- Create VIEW cho READER
	- Create VIEW cho STAFF
	- Create VIEW cho PUBLISHER
	- Create VIEW cho WRITER
	- Create VIEW cho VIOLATION_CATEGORY
	- Insert WRITER
	- Insert PUBLISHER
	- Insert SHELF_BOOK
	- Insert VIOLATION_CATEGORY	
	- Insert READER
	- Insert READER_BLACKLIST
	- Update ALL
	- CRUD STAFF and STAFF_INFO
	- CRUB BOOK

*/

Create Database Library_Management_Biden
Go

Use Library_Management_Biden
GO

IF(OBJECT_ID(N'STAFF', N'U') IS NOT NULL)
	DROP TABLE STAFF
GO
Create Table STAFF
(
	Staff_ID int identity(1,1) Primary key,
	Staff_User char(13) NOT NULL,
	Staff_Pass char(30) NOT NULL,
	IsAdmin bit NOT NULL,
	Designation nvarchar(20) NOT NULL, --VAI TRÒ CỦA NHÂN VIÊN TRONG THƯ VIỆN, VD: Librarian, Assitant,..
	CONSTRAINT CHK_STAFF_IsAdmin CHECK(IsAdmin = 1 OR IsAdmin = 0) --1: Admin; 0: Not Admin
)
GO

IF(OBJECT_ID(N'STAFF_INFO', N'U') IS NOT NULL)
	DROP TABLE STAFF_INFO
GO
Create Table STAFF_INFO
(
	Staff_ID int Primary Key NOT NULL,
	Staff_Name nvarchar(30) NOT NULL,
	Sex nvarchar(5) NOT NULL,
	Date_Of_Birth date NOT NULL,
	Phone_Number char(10) NOT NULL,
	Staff_Address nvarchar(100),
	Staff_Email nvarchar(30),
	In_Date date NOT NULL,
	Out_Date date,
	--Tham chiếu đến thuộc tính Staff_ID của bảng Staff
	CONSTRAINT FK_STAFF_INFO_Staff_ID FOREIGN KEY (Staff_ID) REFERENCES STAFF(Staff_ID),
	CONSTRAINT CHK_STAFF_INFO_Sex CHECK (Sex = N'Nam' OR Sex = N'Nữ' OR Sex = N'Khác')
)
GO



IF(OBJECT_ID(N'READER', N'U') IS NOT NULL)
	DROP TABLE READER
GO
Create Table READER
(
	Reader_ID int identity(1,1) Primary key,
	Reader_Name nvarchar(30) NOT NULL,
	Sex nvarchar(5) NOT NULL,
	Date_Of_Birth date NOT NULL,
	Phone_Number char(10) NOT NULL,
	Reader_Email varchar(50),
	Reader_Address nvarchar(100),
	Register_Date date,
	CONSTRAINT CHK_READER_Sex CHECK (Sex = N'Nam' OR Sex = N'Nữ' OR Sex = N'Khác')
)
GO

IF(OBJECT_ID(N'WRITER', N'U') IS NOT NULL)
	DROP TABLE WRITER
GO
Create Table WRITER
(
	Writer_ID int identity(1,1) Primary key,
	Writer_Name nvarchar(50) NOT NULL,
	Sex nvarchar(5),
	Date_Of_Birth date,
	Phone_Number char(10) NOT NULL,
	Writer_Email varchar(50),
	Writer_Address nvarchar(100),
	CONSTRAINT CHK_WRITER_Sex CHECK (Sex = N'Nam' OR Sex = N'Nữ' OR Sex = N'Khác')
)
Go

IF(OBJECT_ID(N'PUBLISHER', N'U') IS NOT NULL)
	DROP TABLE PUBLISHER
GO
Create Table PUBLISHER
(
	Pub_ID int identity(1,1) Primary key,
	Pub_Name nvarchar(50) NOT NULL
)
GO

IF(OBJECT_ID(N'BOOK_CATEGORY', N'U') IS NOT NULL)
	DROP TABLE BOOK_CATEGORY
GO
Create Table BOOK_CATEGORY
(
	Book_Cate_ID int identity(1,1) Primary key,
	Book_Cate_Name nvarchar(22) NOT NULL
)
Go

IF(OBJECT_ID(N'BOOK', N'U') IS NOT NULL)
	DROP TABLE BOOK
GO
Create Table BOOK
(
	ID int identity(1,1),
	ISBN decimal(13) Primary key, --Ma sach theo co che quoc te
	Book_Name nvarchar(50) NOT NULL,
	Book_Cate_ID int NOT NULL, 
	Writer_ID int NOT NULL, 
	Pub_ID int NOT NULL,
	Publishing_Year int,
	Date_Added Date NOT NULL,
	Book_Image image,
	--Tham chiếu đến thuộc tính Book_Cate_ID của bảng BOOK_CATEGORY
	CONSTRAINT FK_BOOK_Book_Cate_ID FOREIGN KEY (Book_Cate_ID) REFERENCES BOOK_CATEGORY(Book_Cate_ID),
	--Tham chiếu đến thuộc tính Writer_ID của bảng WRITER
	CONSTRAINT FK_BOOK_Writer_ID FOREIGN KEY (Writer_ID) REFERENCES WRITER(Writer_ID),
	--Tham chiếu đến thuộc tính Pub_ID của bảng PUBLISHER
	CONSTRAINT FK_BOOK_Pub_ID FOREIGN KEY (Pub_ID) REFERENCES PUBLISHER(Pub_ID)
)
Go

IF(OBJECT_ID(N'SHELF_BOOK', N'U') IS NOT NULL)
	DROP TABLE SHELF_BOOK
GO
Create Table SHELF_BOOK
(
	Shelf_ID int identity(1,1) Primary key,
	Floor_NO int NULL
)
GO

IF(OBJECT_ID(N'BOOK_STATUS', N'U') IS NOT NULL)
	DROP TABLE BOOK_STATUS
GO
Create Table BOOK_STATUS
(
	ID int identity(1,1),
	ISBN decimal(13) NOT NULL Primary Key,
	Writer_ID int NOT NULL,
	Shelf_ID int NOT NULL,
	Shelf_NO int NOT NULL,
	Floor_NO int NOT NULL,
	Borrowing_Count int DEFAULT 0,
	InStock_Mount int,
	Book_Status bit DEFAULT 1,
	CONSTRAINT FK_BOOK_STATUS_ISBN FOREIGN KEY (ISBN) REFERENCES BOOK(ISBN),
	CONSTRAINT FK_BOOK_STATUS_Writer_ID FOREIGN KEY (Writer_ID) REFERENCES WRITER(Writer_ID),
	CONSTRAINT FK_BOOK_STATUS_Shelf_ID FOREIGN KEY (Shelf_ID) REFERENCES SHELF_BOOK(Shelf_ID),
	CONSTRAINT CHK_BOOK_STATUS_Book_Status CHECK (Book_Status = 1 OR Book_Status = 0), --1: IN STOCK; 0: OUT OF STOCK
	CONSTRAINT CHK_BOOK_STATUS_Stock CHECK (InStock_Mount >= 0)
)
GO

IF(OBJECT_ID(N'BOOK_BORROWING', N'U') IS NOT NULL)
	DROP TABLE BOOK_BORROWING
GO
Create Table BOOK_BORROWING
(
	BKG_ID int identity(1,1) Primary key,
	Reader_ID int NOT NULL, 
	ISBN decimal(13) NOT NULL,
	Borrowed_From_Date date NOT NULL DEFAULT GETDATE(), --MẶC ĐỊNH LẤY NGÀY HIỆN TẠI
	Borrowed_To_Date date NOT NULL,
	Return_Date date,
	Deferred_Payment decimal(18,3),
	Cost_Payment decimal(18,3),
	Borrowing_Status bit NOT NULL DEFAULT 0,
	CONSTRAINT FK_BOOK_BORROWING_Reader_ID FOREIGN KEY (Reader_ID) REFERENCES READER(Reader_ID),
	CONSTRAINT FK_BOOK_BORROWING_ISBN FOREIGN KEY (ISBN) REFERENCES BOOK(ISBN),
	CONSTRAINT CHK_BOOK_BORROWING_Borrowing_Status CHECK (Borrowing_Status = 1 OR Borrowing_Status = 0) --1:Returned; 0: NOT Return
)
GO

IF(OBJECT_ID(N'VIOLATION_CATEGORY', N'U') IS NOT NULL)
	DROP TABLE VIOLATION_CATEGORY
GO
Create Table VIOLATION_CATEGORY
(
	VT_Cate_ID int identity(1,1) Primary key,
	VT_Cate_Name nvarchar(100) NOT NULL
)
GO

IF(OBJECT_ID(N'READER_BLACKLIST', N'U') IS NOT NULL)
	DROP TABLE READER_BLACKLIST
GO
Create Table READER_BLACKLIST
(
	BLACKLIST_ID int identity(1,1) Primary key,
	Reader_ID int NOT NULL,
	VT_Cate_ID int NOT NULL
	CONSTRAINT FK_READER_BLACKLIST_Reader_ID FOREIGN KEY (Reader_ID) REFERENCES READER(Reader_ID),
	CONSTRAINT FK_READER_BLACKLIST_VT_Cate_ID FOREIGN KEY (VT_Cate_ID) REFERENCES VIOLATION_CATEGORY(VT_Cate_ID)
)
Go

IF(OBJECT_ID(N'REVENUE', N'U') IS NOT NULL)
	DROP TABLE REVENUE
GO
Create Table REVENUE
(
	Revenue_ID int identity(1,1) Primary key,
	Revenue_Date char(8) NOT NULL DEFAULT CONVERT(char(8),GETDATE(),112), --DẠNG KIỂU DỮ LIỆU CHAR CHỨ KHÔNG PHẢI DATE, CÓ DẠNG CHUỖI yyyyMMdd 
	Gain decimal(18,3) NOT NULL
)
GO

IF(OBJECT_ID(N'COPYRIGHT_FEE', N'U') IS NOT NULL)
	DROP TABLE COPYRIGHT_FEE
GO
Create Table COPYRIGHT_FEE
(
	CPR_Fee_ID int identity (1,1) Primary key,
	CPR_Fee_Date char(8) NOT NULL DEFAULT CONVERT(char(8),GETDATE(),112), --DẠNG KIỂU DỮ LIỆU CHAR CHỨ KHÔNG PHẢI DATE, CÓ DẠNG CHUỖI yyyyMMdd 
	ISBN decimal(13) NOT NULL,
	Writer_ID int NOT NULL,
	CPR_Fee decimal(18,3) NOT NULL,
)
Go

IF(OBJECT_ID(N'STATISTIC', N'U') IS NOT NULL)
	DROP TABLE STATISTIC
GO
Create Table STATISTIC
(
	ID int identity (1,1) NOT NULL,
	Stat_Code char(6) Primary key DEFAULT SUBSTRING(CONVERT(char(8),GETDATE(),112),1,6), --DẠNG KIỂU DỮ LIỆU CHAR CHỨ KHÔNG PHẢI DATE, CÓ DẠNG CHUỖI yyyyMM 
	Revenue decimal(18,3),
	CPR_Fee decimal(18,3),
	Profit decimal(18,3),
	Growth tinyint
)
GO

IF(OBJECT_ID(N'BOOK_isDeleted', N'U') IS NOT NULL)
	DROP TABLE BOOK_isDeleted
GO
--Tạo bảng xem sách đó còn đã xoá khỏi kho hay không
Create Table BOOK_isDeleted
(
	ISBN decimal(13) Primary key, --Ma sach theo co che quoc te
	Del_Date Date NOT NULL DEFAULT GETDATE(),
	CONSTRAINT FK_BOOK_isDeleted_ISBN FOREIGN KEY(ISBN) REFERENCES BOOK(ISBN)
)
GO

Insert BOOK_CATEGORY Values
(
	N'Programming'
),
(
	N'Giáo dục'
)
Go

Insert WRITER Values
(
	N'Nguyễn Đức Nghĩa', N'Nam', NULL, '0903210111', N'nghiand@it-hut.edu.vn', N'Trường Đại Học Bách Khoa Hà Nội'
),
(
	N'Nguyễn Xuân Đạt', N'Nam',	NULL, '0564131987', N'datnx@gmail.com', N'Trường Đại Học Thủ Dầu Một'
)
GO

Insert PUBLISHER Values
(
	N'Nhà xuất bản bách khoa Hà Nội'
),
(
	N'Nhà xuất bản Đại học quốc gia TP.Hồ Chí Minh'
)
GO

Insert BOOK Values
(
	9786049931130, N'Cấu trúc dữ liệu và giải thuật', (Select Book_Cate_ID From BOOK_CATEGORY Where Book_Cate_Name = 'Programming'), (Select Writer_ID From WRITER Where Writer_Name = N'Nguyễn Đức Nghĩa'), (Select Pub_ID From PUBLISHER Where Pub_Name = N'Nhà xuất bản bách khoa Hà Nội'), NULL, '2021-06-17','test1.png'
)
GO

Insert SHELF_BOOK Values
(
	1
)
GO

Insert BOOK_STATUS Values
(
	9786049931130, 1, 1, 1, 1, 0, 11, 1
)
GO

Insert READER Values
(	
	N'Joe Biden', 'Nam', '1942-11-20', '0123456789', N'TongThongMi@gmail.com', N'America', '2021-06-17'
)
GO

Insert BOOK_BORROWING(Reader_ID, ISBN, Borrowed_To_Date) Values
(
	1, 9786049931130, '2021-06-20'
)
GO

/*
Delete from BOOK_BORROWING
DBCC CHECKIDENT ('BOOK_BORROWING',Reseed,0)
*/
Insert STAFF values
(
	'admin', 'admin', 1, 'Manager'
)
GO

Insert STAFF_INFO values
(
	1, N'Donald Trump', 'Nam', '1946-06-14', '0987654321', 'America', Null, '2021-06-16', '2021-06-17'
)
GO

IF(OBJECT_ID(N'sp_Login', N'P') IS NOT NULL)
	Drop PROC sp_Login
GO
--Login
Create PROC sp_Login(@user char(13), @pass char(30))
AS
Begin
	Select * 
	From STAFF 
	Where  Staff_User = @user AND Staff_Pass = @pass
End
GO

IF(OBJECT_ID(N'sp_UpdateALL', N'P') IS NOT NULL)
	Drop PROC sp_UpdateALL
GO
--Update cho tất cả bảng
Create PROC sp_UpdateALL (@table nvarchar(20), @setClause nvarchar(1000), @whereClause nvarchar(1000))
AS
Begin
	Set NOCOUNT ON
	Declare @updateALL nvarchar(max)
	Begin
		Select @updateALL = 'UPDATE ' + @table + ' SET ' + @setClause + ' WHERE 1=1 AND ' + @whereClause
		EXECUTE(@updateALL)
	End
End
GO

--Select * From STAFF_INFO
/*
	~~DEMO~~
						[Bảng]			[Cột update]				[Điều kiện]
EXECUTE sp_UpdateALL 'STAFF', N'Designation = N''Giữ tàn kinh các''' , N'Staff_ID = 4'
*/

IF(OBJECT_ID(N'sp_InsertSTAFF', N'P') IS NOT NULL)
	Drop PROC sp_InsertSTAFF
GO
--INSERT cho Staff
Create PROC sp_InsertSTAFF(@user nvarchar(13), @pass nvarchar(100), @isAdmin bit, @designation nvarchar(20), @name nvarchar(30), @sex nvarchar(5), @birth date, @phone char(10), @address nvarchar(100), @email nvarchar(30), @in date)
AS
Begin
	Set NOCOUNT ON
	INSERT STAFF(Staff_User, Staff_Pass, IsAdmin, Designation)
	VALUES (@user, @pass, @isAdmin, @designation)

	Insert STAFF_INFO (Staff_ID, Staff_Name, Sex, Date_Of_Birth, Phone_Number, Staff_Address, Staff_Email, In_Date)
	--@id t lấy cột bên user để set Staff_ID cho STAFF_INFO nên vì thế nó là nvarchar thay vì là int
	VALUES ((Select Staff_ID From STAFF Where Staff_User = @user), @name, @sex, @birth, @phone, @address, @email, @in)
End
Go

/*
Select * From STAFF
Select * From STAFF_INFO
*/

/*
						[User]		[Pass]		[isAdmin]	[Designation]			[Name]			 [GT]	  [Ngày sinh	  [SĐT]			[Cư trú]  [Email] [Ngày vào làm]
EXECUTE sp_InsertSTAFF N'Khanh8017', N'KhanhHanMacTu', 0, N'Giữ bí kíp họ Lê', N'Khánh Khủng Khiếp', N'Nam', '2000-08-17', '0123456789', N'Bình Dương', NULL, '2021-06-30'
*/

IF(OBJECT_ID(N'sp_InsertBOOK_CATE', N'P') IS NOT NULL)
	Drop PROC sp_InsertBOOK_CATE
GO
--INSERT cho BOOK_Category
Create PROC sp_InsertBOOK_CATE(@name nvarchar(22))
AS
Begin
	INSERT BOOK_CATEGORY(Book_Cate_Name)
	VALUES (@name)
End
GO

--Select * From BOOK_CATEGORY
/*
EXECUTE sp_InsertBOOK_CATE N'Xã hội'
*/

IF(OBJECT_ID(N'sp_InsertPUNLISHER', N'P') IS NOT NULL)
	Drop PROC sp_InsertPUNLISHER
GO
--INSERT cho BOOK_Category
Create PROC sp_InsertPUNLISHER(@name nvarchar(50))
AS
Begin
	INSERT PUBLISHER(Pub_Name)
	VALUES (@name)
End
GO

--Select * From PUBLISHER
/*
EXECUTE sp_InsertPUNLISHER N'Nhà xuất bản Nhi Đồng'
*/

IF(OBJECT_ID(N'sp_InsertWRITER', N'P') IS NOT NULL)
	Drop PROC sp_InsertWRITER
GO
--INSERT cho WRITER
Create PROC sp_InsertWRITER(@name nvarchar(50), @sex nvarchar(5), @birth date, @phone char(10), @email nvarchar(50), @address nvarchar(100))
AS
Begin
	INSERT WRITER(Writer_Name, Sex, Date_Of_Birth, Phone_Number, Writer_Email, Writer_Address)
	VALUES (@name, @sex, @birth, @phone, @email, @address)
End
GO

--Select * From WRITER
/*							[Tên*]				[GT*]	[NS]	[SĐT*]		[Email]				[Địa chỉ]
EXECUTE sp_InsertWRITER N'Dương Hoàng Thị Oanh', N'Nữ', NULL, '0321654987', N'oanhdht@gmail.com', N'Đại học Thủ Dầu Một'
*/

IF(OBJECT_ID(N'sp_InsertSHELF_BOOK', N'P') IS NOT NULL)
	Drop PROC sp_InsertSHELF_BOOK
GO
--INSERT cho SHELF_BOOK
Create PROC sp_InsertSHELF_BOOK(@no int)
AS
Begin
	INSERT SHELF_BOOK(Floor_NO)
	VALUES (@no)
End
GO

--Select * From SHELF_BOOK
/*
EXECUTE sp_InsertSHELF_BOOK 4
*/

IF(OBJECT_ID(N'sp_InsertBOOK', N'P') IS NOT NULL)
	Drop PROC sp_InsertBOOK
GO
--INSERT cho BOOK
Create PROC sp_InsertBOOK(@ISBN decimal(13), @name nvarchar(50), @bookcate_ID nvarchar(100), @writer nvarchar(50), @pub nvarchar(50), @pub_year int, @add date, @image image, @shelf_ID int, @shelf_NO int, @Stock int)
AS
Begin
	INSERT BOOK (ISBN, Book_Name, Book_Cate_ID, Writer_ID, Pub_ID, Publishing_Year, Date_Added, Book_Image)
		--[ISBN*] [Tên*]					[Gõ tên book cate sẽ hiển thị ID*]											[Gõ tên nhà văn sẽ hiển thị ID*]					[Gõ tên nhà xuất bản sẽ hiển thị ID*]		[Năm phát hành] [Ngày thêm vào thư viện*]
	VALUES (@ISBN, @name, (Select Book_Cate_ID From BOOK_CATEGORY Where Book_Cate_Name = @bookcate_ID), (Select Writer_ID From WRITER Where Writer_Name = @writer), (Select Pub_ID From PUBLISHER Where Pub_Name = @pub), @pub_year, @add, @image)

	--Sau khi thêm sách vào BOOK thì tự động thêm vào BOOK_STATUS
	INSERT BOOK_STATUS (ISBN, Writer_ID, Shelf_ID, Shelf_NO, Floor_NO, InStock_Mount)

	VALUES (@ISBN, (Select Writer_ID From WRITER Where Writer_Name = @writer), @shelf_ID, @shelf_NO, (Select Floor_NO From SHELF_BOOK Where @shelf_ID = Shelf_ID), @Stock)
End
GO

--Select * From BOOK_STATUS
/*
	~~DEMO~~
EXECUTE sp_InsertBOOK '9786047364596', N'Tư duy biện luận ứng dụng', N'Giáo dục', N'Nguyễn Xuân Đạt', N'Nhà xuất bản đại học quốc gia TP.Hồ Chí Minh', NULL, '2021-06-30','test2.png', 1, 2, 13
*/

--INSERT cho VIOLATION_CATEGORY
IF(OBJECT_ID(N'sp_InsertVIOLATION_CATEGORY', N'P') IS NOT NULL)
	Drop PROC sp_InsertVIOLATION_CATEGORY
GO
Create PROC sp_InsertVIOLATION_CATEGORY(@content nvarchar(100))
AS
Begin
	SET NOCOUNT ON
	INSERT VIOLATION_CATEGORY(VT_Cate_Name)
	VALUES	(@content)
End
GO

--Select * From VIOLATION_CATEGORY
/*
EXECUTE sp_InsertVIOLATION_CATEGORY N'Gây hư hỏng sách hoặc làm mất sách'
*/

IF(OBJECT_ID(N'sp_InsertREADER', N'P') IS NOT NULL)
	Drop PROC sp_InsertREADER
GO
--INSERT cho READER
Create PROC sp_InsertREADER(@name nvarchar(30), @sex nvarchar(5), @birth date, @phone char(10), @email nvarchar(50), @address nvarchar(100), @register date)
AS
Begin
	SET NOCOUNT ON
	INSERT READER(Reader_Name, Sex, Date_Of_Birth, Phone_Number, Reader_Email, Reader_Address, Register_Date)
	VALUES (@name, @sex, @birth, @phone, @email, @address, @register)
End
GO

--Select * From READER
/*
							[Tên*]			   [GT*]		[NS*]		[SĐT*]			[Email]						[Địa chỉ]	[Ngày đk]
EXECUTE sp_InsertREADER N'Lê Văn Vương Khánh', N'Nam', '2000-08-17', '0852147963', N'khanhkhungkhiep@gmail.com', N'Bình Dương', '2021-07-03'
*/

IF(OBJECT_ID(N'sp_InsertREADER_BLACKLIST', N'P') IS NOT NULL)
	Drop PROC sp_InsertREADER_BLACKLIST
GO
Create PROC sp_InsertREADER_BLACKLIST(@name nvarchar(30), @birth date, @content nvarchar(100))
AS
Begin
	SET NOCOUNT ON
	INSERT READER_BLACKLIST(Reader_ID, VT_Cate_ID)
	VALUES ((Select Reader_ID From READER Where @name = Reader_Name AND @birth = Date_Of_Birth), (Select VT_Cate_ID From VIOLATION_CATEGORY Where VT_Cate_Name LIKE N'%' + @content + '%'))
End
GO

--Select * From READER_BLACKLIST
/*

EXECUTE sp_InsertREADER_BLACKLIST N'Lê Văn Vương Khánh', '2000-08-17', N'mất sách'
*/

IF(OBJECT_ID(N'sp_InsertBOOK_BORROWING', N'P') IS NOT NULL)
	Drop PROC sp_InsertBOOK_BORROWING
GO
Create PROC sp_InsertBOOK_BORROWING(@reader_ID int, @ISBN decimal(13), @todate date)
AS
Begin
	SET NOCOUNT ON
	Declare @TranCounter int
	Set @TranCounter = @@TRANCOUNT
	IF(@TranCounter > 0)
		SAVE TRAN Savepoint
	ELSE
		Begin TRAN
			--Nếu đọc giả vi phạm 5 xuống thì có thể mượn sách
			IF((Select Count(Reader_ID) From READER_BLACKLIST Where Reader_ID = @reader_ID) < 5)
			Begin
				INSERT BOOK_BORROWING(Reader_ID, ISBN, Borrowed_To_Date)
				VALUES (@reader_ID, @ISBN, @todate)

				IF(@TranCounter = 0)
					COMMIT TRAN
			End
			ELSE
			--Còn vi phạm từ 5 lần trở lên thì không thể thực hiện mượn sách
			Begin
				IF(@TranCounter = 0)
				Begin
					PRINT(N'Đọc giả đã vi phạm từ 5 lần trở lên nên không thể thực hiện mượn sách')	
					ROLLBACK TRAN
				End
			End
End
GO

/*
	Select * From BOOK_BORROWING
									[Reader_ID]		[ISBN]	   [Mượn đến ngày]
	EXECUTE sp_InsertBOOK_BORROWING		1,		9786047364596,	'2021-07-23'
*/

-- Tạo VIEW cho BOOK

Create VIEW view_BOOK
AS
	Select B.ISBN, B.Book_Name AS N'Tên sách', BC.Book_Cate_Name AS N'Danh mục sách', W.Writer_Name AS N'Tác giả', P.Pub_Name N'Nhà xuất bản', B.Publishing_Year AS N'Năm phát hành', B.Date_Added AS N'Ngày nhập'
	From (((BOOK B inner join PUBLISHER P on B.Pub_ID = P.Pub_ID)
				   inner join WRITER W on B.Writer_ID = W.Writer_ID)
				   inner join BOOK_CATEGORY BC on B.Book_Cate_ID = BC.Book_Cate_ID)
	Where B.ISBN NOT IN (
							Select ISBN
							From BOOK_isDeleted BI
							Where B.ISBN = BI.ISBN
						)
GO
--Select * From dbo.view_BOOK

Create VIEW view_BOOK_STATUS
AS
Select DISTINCT B.ISBN, B.Book_Name AS N'Tên sách', BS.Shelf_ID AS N'Tủ sách số', BS.Shelf_NO AS N'Ngăn', BS.Floor_NO AS N'Tầng', BS.InStock_Mount AS N'Số lượng còn', BS.Borrowing_Count AS N'Đã mượn', CASE
																																																			When BD.Del_Date IS NOT NULL
																																																				Then N'Sách không còn trong kho'
																																																			Else
																																																				N'Sách còn trong kho'
																																																		END AS N'Trong kho', BD.Del_Date AS N'Ngày xoá'
	From (((BOOK B left join BOOK_isDeleted BD on B.ISBN = BD.ISBN)
				   left join BOOK_STATUS BS on B.ISBN = BS.ISBN)
				   left join BOOK_BORROWING BB on B.ISBN = BB.ISBN)
GO
--Select * from dbo.view_BOOK_STATUS

Create VIEW view_READER
AS
	Select DISTINCT R.Reader_ID AS N'ID', R.Reader_Name AS N'Tên đọc giả', R.Sex AS N'Giới tính', R.Date_Of_Birth AS N'Ngày sinh', R.Reader_Email AS N'Email', R.Reader_Address AS N'Địa chỉ', R.Register_Date AS N'Ngày đăng kí', COUNT(RB.Reader_ID) AS N'Số lần vi phạm'
	From READER R left join READER_BLACKLIST RB on R.Reader_ID = RB.Reader_ID
	Group by R.Reader_ID, R.Reader_Name, R.Sex,R.Date_Of_Birth, R.Reader_Email, R.Reader_Address, R.Register_Date
GO
--Select * From dbo.view_READER

Create VIEW view_STAFF_INFO
AS
	Select S.Staff_ID AS N'Mã nhân viên', S.Staff_User AS N'Tên đăng nhập', S.Staff_Pass AS N'Mật khẩu', SI.Staff_Name AS N'Tên nhân viên', SI.Sex AS N'Giới tính', SI.Date_Of_Birth AS N'Ngày sinh', SI.Phone_Number AS N'Số điện thoại', SI.Staff_Address AS N'Địa chỉ', SI.Staff_Email AS N'Email', S.Designation AS N'Chức vụ', SI.In_Date AS N'Ngày vào làm', CASE
																																																																																										When SI.Out_Date IS NOT NULL
																																																																																											Then N'Đã nghỉ việc'
																																																																																										Else
																																																																																											N'Còn tại vị'
																																																																																									END AS N'Tình trạng', Out_Date AS N'Ngày nghỉ'
	From STAFF S inner join STAFF_INFO SI on S.Staff_ID = SI.Staff_ID
GO
--Select * From view_STAFF

Create VIEW view_PUBLISHER
AS
	Select *
	From PUBLISHER
GO
--Select * From dbo.view_PUBLISHER

Create VIEW view_WRITER
AS
	Select *
	From WRITER
GO
--Select * From dbo.view_WRITER

Create VIEW view_VIOLATION_CATEGORY
AS
	Select *
	From VIOLATION_CATEGORY
GO
--Select * From dbo.view_VIOLATION_CATEGORY

--Tạo VIEW cho sách được mượn chưa trả
Create VIEW view_BORROW_NOT_RETURN
AS
	Select * 
	From BOOK_BORROWING
	Where Borrowing_Status = 0
GO
--Select * From dbo.view_BORROW_NOT_RETURN

--Tạo VIEW cho sách được mượn nhiều nhất TOP 10
Create VIEW view_MOST_BORROWING
AS
	Select TOP 10 B.ISBN AS N'Mã sách', W.Writer_Name N'Tác giả', P.Pub_Name N'Nhà xuất bản', COUNT(BB.ISBN) AS 'Số Lần Mượn Sách'
	FROM (((BOOK_BORROWING BB left join BOOK B on BB.ISBN = B.ISBN)
								inner join WRITER W on B.Writer_ID = W.Writer_ID)
								inner join PUBLISHER P on B.Pub_ID = P.Pub_ID)
	Group by B.ISBN, W.Writer_Name, P.Pub_Name
	Order by [Số Lần Mượn Sách] desc
GO
--Select * From view_MOST_BORROWING

--Update Cost_Payment trong Book_Borrowing
IF (OBJECT_ID(N'sp_UpdateCost', N'P') IS NOT NULL)
	DROP PROC sp_UpdateCost
GO
Create PROC sp_UpdateCost(@bkg_ID int)
AS
Begin
	SET NOCOUNT ON
	--Set return_date là ngày hiện tại
	Declare @return_date date
	SET @return_date = GETDATE()
	--Set Trancounter bằng với TRANCOUNT (Đây là bộ đếm xem đang chạy transaction nào)
	Declare @TranCounter int
	Set @TranCounter = @@TRANCOUNT
	--Nếu lớn hơn 0 tức là có ít nhất 1 transaction đang chạy
	--Nên t tạo 1 điểm save để rollback nếu có 1 lỗi xảy ra
	IF(@TranCounter > 0)
		SAVE TRAN Savepoint
	ELSE
		Begin TRAN
			Begin TRY
				--Nếu đã thanh toán rồi thì sẽ k thực hiện thanh toán lại và rollback transaction
				IF((Select Borrowing_Status From BOOK_BORROWING Where BKG_ID = @bkg_ID) = 1 OR not exists (Select * From BOOK_BORROWING Where BKG_ID = @bkg_ID))
				Begin
					PRINT N'Đã thanh toán hoặc không có mã mượn đó trong danh sách'
					ROLLBACK TRAN
				End
				--Chưa thanh toán thì chạy tiếp
				ELSE
				Begin
					--Tạo biến tạm để lưu trữ gain đã vừa thanh toán
					Declare	@Gain decimal(18,3)
					--Nếu Borrowed_To_Date (ngày hẹn trả) mà lớn hơn (tức trả sớm hơn) return_date (Ngày trả) 
					--Thì t sẽ set cho nó bằng với ngày hẹn trả
					IF((Select CAST(DATEDIFF(DAY, @return_date, (Select Borrowed_To_Date From BOOK_BORROWING Where BKG_ID = @bkg_ID)) AS int)) >= 0)
						Begin
							Update BOOK_BORROWING
							Set Return_Date = (Select Borrowed_To_Date From BOOK_BORROWING Where BKG_ID = @bkg_ID),
								Deferred_Payment = 0,
								--Phí thuê là 400/ngày
								--Cost = 400 * (Từ ngày mượn đến ngày hẹn trả)
								Cost_Payment = 400 * CAST(DATEDIFF(DAY, Borrowed_From_Date, Borrowed_To_Date) AS int),
								--Set thành 1 là thể hiện trả rồi
								Borrowing_Status = 1
							Where BKG_ID = @bkg_ID

							Update BOOK_STATUS
							--Sau khi thanh toán thì tăng số lượng sách được mượn lên 1 và tăng số lượng trong kho lên 1
							Set Borrowing_Count += 1,
								InStock_Mount += 1
							From BOOK_STATUS BS
							Where BS.ISBN = (Select BG.ISBN From BOOK_BORROWING BG Where BKG_ID = @bkg_ID)
							
							-- Set cho biến tạm số tiền vừa thanh toán
							Set @Gain = (Select Cost_Payment From BOOK_BORROWING Where @bkg_ID = BKG_ID)
							INSERT REVENUE (Revenue_Date, Gain)
							--Revenue_Date bản chất là date nhưng k có gạch ở giữa (kiểu viết liền mạch), @Gain là số tiền vừa được gán 
							VALUES ((Select CONVERT(char(8), GETDATE(), 112)), @Gain)

							--Nếu không có transaction nào chạy trước khi cái proc này được gọi
							--Thì proc này phải commit cái transaction mà nó đã chạy
							IF(@TranCounter = 0)
								COMMIT TRAN
						End
					--Nếu return_date (ngày trả) lớn hơn (trả trễ) Borrowed_To_Date (ngày hẹn trả)
					ELSE
					Begin
						Update BOOK_BORROWING
						--Thì set return_date bằng ngày hiện tại
						Set Return_Date = @return_date,
							--Phí trả trễ là 500/ngày
							--Deferred_Payment = 500 * (Số ngày trễ)
							Deferred_Payment = 500 * CAST(DATEDIFF(DAY, Borrowed_To_Date, @return_date) AS int),
							--Cost = 400 * (Số ngày hẹn + Số ngày trả trễ)
							Cost_Payment = 400 * CAST(DATEDIFF(DAY, Borrowed_From_Date, Borrowed_To_Date)  AS int) + 500 * CAST(DATEDIFF(DAY, Borrowed_To_Date, @return_date) AS int),
							Borrowing_Status = 1
						Where BKG_ID = @bkg_ID
						
						Update BOOK_STATUS
							--Sau khi thanh toán thì tăng số lượng sách được mượn lên 1 và tăng số lượng trong kho lên 1
						Set Borrowing_Count += 1,
							InStock_Mount += 1
						From BOOK_STATUS BS
						Where BS.ISBN = (Select BG.ISBN From BOOK_BORROWING BG Where BKG_ID = @bkg_ID)

						-- Set cho biến tạm số tiền vừa thanh toán
						Set @Gain = (Select Cost_Payment From BOOK_BORROWING Where @bkg_ID = BKG_ID)
						INSERT REVENUE (Revenue_Date, Gain)
						--Revenue_Date bản chất là date nhưng k có gạch ở giữa (kiểu viết liền mạch), @Gain là số tiền vừa được gán 
						VALUES ((Select CONVERT(char(8), GETDATE(), 112)), @Gain)
						--Như cái cmt của cái commit trên
						IF(@TranCounter = 0)
							COMMIT TRAN
					End
				End
			End TRY
			Begin CATCH
				--Nếu có 1 lỗi xảy ra thì rollback lại những transaction mà xảy ra lỗi
				--Hoặc chưa được commit
				IF(@TranCounter = 0)
					ROLLBACK TRAN
				ELSE
					--Transaction mà chạy trước khi proc này được gọi thì không rollback các thay đã thực thi 
					Begin
						IF(XACT_STATE() <> -1)
							--Nếu vẫn có một transaction vẫn còn chạy, thì quay về điểm Save mà t tạo hồi nãy
							ROLLBACK TRAN Savepoint
					End

				--Sau khi rollback một lỗi phát hiện, in thông tin lỗi đó
				Declare @ErrorMessage nvarchar(4000)
				Declare @ErrorSeverity int
				Declare @ErrorState int

				Select @ErrorMessage = ERROR_MESSAGE()
				Select @ErrorSeverity = ERROR_SEVERITY()
				Select @ErrorState = ERROR_STATE()

				RAISERROR  (@ErrorMessage, --Message text
							@ErrorSeverity, --Severity
							@ErrorState --State
							)
			End CATCH
End
Go

IF(OBJECT_ID(N'trg_UpdateStock', N'TR') IS NOT NULL)
	DROP TRIGGER trg_UpdateStock
GO
Create TRIGGER trg_UpdateStock on BOOK_BORROWING
AFTER INSERT
AS
Begin
	--Tạo Trancounter để điểm số Transaction đang chạy
	Declare @TranCounter int
	Set @TranCounter = @@TRANCOUNT
	--Tạo LastID để lấy mã BKG_ID cuối cùng của bảng BOOK_BORROWING để có thể lấy đc mã ISBN của nó
	Declare @LastID int
	Select @LastID = MAX(BKG_ID) FROM BOOK_BORROWING
	--Tạo ISBN để set nó là mã của sách cuối cùng đang được mượn để cập nhật lại trong BOOK_STATUS
	Declare @ISBN decimal(13)
	Select @ISBN = ISBN From BOOK_BORROWING Where @LastID = BKG_ID
	--Tạo Counter_0 để lấy dữ liệu vừa mới thêm vào trong BOOK_BORROWING
	Declare @Counter_0 int
	Select @Counter_0 = @@ROWCOUNT From BOOK_BORROWING
	IF(@TranCounter > 0)
		SAVE TRAN SavePoint
	ELSE
	Begin TRAN
		Begin TRY
			--Nếu có mã sách đó hết trong kho thì thực hiện rollback transaction
			IF((Select InStock_Mount From BOOK_STATUS Where @ISBN = ISBN) = 0)
			Begin
				PRINT N'Kho sách hết loại sách này'
				ROLLBACK TRAN
			End
			--Nếu còn thì thực hiện các phép tính
			ELSE
			Begin
				Update BOOK_STATUS
				Set InStock_Mount = InStock_Mount - @Counter_0
				Where ISBN = @ISBN
				--Nếu không có transaction nào thực hiện trước đó thì commit cái tran này
				IF(@TranCounter = 0)
					COMMIT TRAN
			End
		End TRY
		Begin CATCH
			--Nếu có lỗi đồng thời xảy ra trong lúc thực hiện transaction trong trigger này thì rollback lại trước khi thực hiện
			IF(@TranCounter = 0)
				ROLLBACK TRAN
			ELSE
			--Transaction mà chạy trước khi proc này được gọi thì không rollback các thay đã thực thi 
			Begin
				IF(XACT_STATE() <> -1)
					--Nếu vẫn có một transaction vẫn còn chạy, thì quay về điểm Save mà t tạo hồi nãy
					ROLLBACK TRAN Savepoint
			End

			--Sau khi rollback một lỗi phát hiện, in thông tin lỗi đó
			Declare @ErrorMessage nvarchar(4000)
			Declare @ErrorSeverity int
			Declare @ErrorState int

			Select @ErrorMessage = ERROR_MESSAGE()
			Select @ErrorSeverity = ERROR_SEVERITY()
			Select @ErrorState = ERROR_STATE()

			RAISERROR  (@ErrorMessage, --Message text
						@ErrorSeverity, --Severity
						@ErrorState --State
						)
		End CATCH
End
GO


/*
Select * From view_BOOK_STATUS
Drop Trigger trg_UpdateStock
Select * From BOOK_STATUS
EXECUTE sp_InsertBOOK_BORROWING  1, 9786049931130, '2021-07-26'
EXECUTE sp_UpdateCost 2
Select * From REVENUE
Select * From BOOK_BORROWING
*/

-- XOÁ SÁCH TRONG KHO (THỰC RA KHÔNG XOÁ HOÀN TOÀN MÀ CHỈ ĐÁNH DẤU NÓ VÀ KHÔNG CHO NÓ HIỆN RA, CHỨ NÓ VẪN CÒN TRONG CSDL)
IF(OBJECT_ID(N'sp_DeleteBOOK', N'P') IS NOT NULL)
	DROP PROC sp_DeleteBOOK
GO

Create PROC sp_DeleteBOOK (@ISBN decimal(13))
AS
Begin
	SET NOCOUNT ON
	-- Cái này có ở trên rồi nên không nhắc lại nữa
	Declare @TranCounter int
	Set @TranCounter = @@TRANCOUNT
	IF(@TranCounter > 0)
		SAVE TRAN SavePoint
	ELSE
	Begin TRAN
		Begin TRY
			-- Nếu tồn tại mã sách này trong BOOK_BORROWING (tức là đang có đọc giả mượn thì k thực thi việc xoá sách mà hiện lên nhắc xong rollback
			-- và thực hiện kết thúc cái proc này) Hoặc là đã thực hiện thao tác xoá trước đây rồi thì k thể thực hiện được nữa
			IF(EXISTS (Select ISBN From BOOK_isDeleted Where @ISBN = ISBN) OR EXISTS (Select ISBN From BOOK_BORROWING Where @ISBN = ISBN))
			Begin
				PRINT N'Sách đang được đọc giả mượn hoặc đã thực hiện xoá rồi nên không thể thực hiện thao tác này được. Hãy kiểm tra lại'
				ROLLBACK TRAN
			End
			-- Nếu không có ai đang mượn thì thực hiện việc xoá sách
			ELSE
			Begin
				-- Nhập ISBN để xoá sách
				INSERT BOOK_isDeleted (ISBN)
				VALUES (@ISBN)
				-- Cập nhật lại số lượng trong kho về 0 khi xoá sách
				UPDATE BOOK_STATUS
				SET InStock_Mount = 0, Book_Status = 0
				WHERE @ISBN = ISBN
				-- Xác nhận thực thi cái tran này và kết thúc (Còn nếu có lỗi thì thực hiện cái catch bên dưới)
				IF(@TranCounter = 0)
					COMMIT TRAN
			End
		End TRY
		Begin CATCH
			IF(@TranCounter = 0)
				ROLLBACK TRAN
			ELSE
			Begin
				IF(XACT_STATE() <> -1)
					ROLLBACK TRAN SavePoint
			End

			Declare @ErrorMessage nvarchar(4000)
			Declare @ErrorSeverity int
			Declare @ErrorState int

			Select @ErrorMessage = ERROR_MESSAGE()
			Select @ErrorSeverity = ERROR_SEVERITY()
			Select @ErrorState = ERROR_STATE()

			RAISERROR  (@ErrorMessage, --Message text
						@ErrorSeverity, --Severity
						@ErrorState --State
						)
		End CATCH

End
GO

/*
Select * From BOOK_isDeleted

EXEC sp_DeleteBOOK 9786047364596
*/

-- Hoàn tác việc xoá sách
IF(OBJECT_ID(N'sp_UndeletedBOOK', N'P') IS NOT NULL)
	DROP PROC sp_UndeletedBOOK
Go

Create PROC sp_UndeletedBOOK (@ISBN decimal(13), @Stock int)
AS
Begin
	SET NOCOUNT ON
	Declare @TranCounter int
	Set @TranCounter = @@TRANCOUNT
	IF(@TranCounter > 0)
		SAVE TRAN SavePoint
	ELSE
	Begin TRAN
		Begin TRY
			-- Nếu sách đó chưa được xoá hoặc không tồn tại thì không thể thực hiện tran này
			IF(NOT EXISTS (Select ISBN From BOOK_isDeleted Where @ISBN = ISBN))
			Begin
				PRINT N'Mã sách chưa được xoá hoặc không tồn tại'
				ROLLBACK TRAN
			End
			ELSE
			Begin
				-- Xoá hẳn sách đó trong BOOK_isDeleted
				Delete From BOOK_isDeleted
				Where @ISBN = ISBN
				-- Update lại các thuộc tính như [số lượng trong kho], [Tình trạng sách] (1 là tồn tại)
				UPDATE BOOK_STATUS
				SET InStock_Mount = @Stock,
					Book_Status = 1
				Where ISBN = @ISBN

				IF(@TranCounter = 0)
					COMMIT TRAN
			End
		End TRY
		Begin CATCH
			--Cái này ở trên nói mãi r nên k cmt nữa
			IF(@TranCounter = 0)
				ROLLBACK TRAN
			ELSE
			Begin
				IF(XACT_STATE() <> -1)
					ROLLBACK TRAN SavePoint
			End
			Declare @ErrorMessage nvarchar(4000)
			Declare @ErrorSeverity int
			Declare @ErrorState int

			Select @ErrorMessage = ERROR_MESSAGE()
			Select @ErrorSeverity = ERROR_SEVERITY()
			Select @ErrorState = ERROR_STATE()

			RAISERROR  (@ErrorMessage, --Message text
						@ErrorSeverity, --Severity
						@ErrorState --State
						)
		End CATCH
		
End
Go
/*
EXEC sp_UndeletedBOOK 9786047364596, 15
Select * From BOOK_isDeleted
Select * From dbo.view_BOOK_STATUS
*/

--THÊM DỮ LIỆU VÀO BẢNG COPY_RIGHT
IF(OBJECT_ID(N'sp_InsertCOPYRIGHT_FEE', N'P') IS NOT NULL)
	DROP PROC sp_InsertCOPYRIGHT_FEE
GO
Create PROC sp_InsertCOPYRIGHT_FEE (@ISBN decimal(13), @WriterID int, @Fee decimal(18,3))
AS
Begin
	SET NOCOUNT ON
	--Cái này chỗ nào cũng nói r nên k nói nữa
	Declare @TranCounter int
	Set @TranCounter = @@TRANCOUNT
	IF(@TranCounter > 0)
		SAVE TRAN SavePoint
	ELSE
	Begin TRAN
		Begin TRY
			--Tạo 1 biến để lấy ngày hiện tại
			Declare @CurrentDate char(8)
			--Set biến đó theo ngày hiện tại cụ thể là yyyyMMDD
			Set @CurrentDate = CONVERT(Char(8),GETDATE(),112)
			--Lấy 6 số đầu tức năm và tháng so với 6 số đầu của cột CPR_FEE_Date trong bảng COPYRIGHT_FEE
			--Nếu cùng tháng, năm, cùng cột ISBN và Writer ID thì phí bản quyền của dòng đó lên
			IF((Select CAST(SUBSTRING(@CurrentDate,1,6) AS int)) = (Select TOP 1 CAST(SUBSTRING(CPR_Fee_Date,1,6) AS int) From COPYRIGHT_FEE Where @ISBN = ISBN AND Writer_ID = @WriterID ORDER BY CPR_Fee_ID desc))
			Begin
				Update COPYRIGHT_FEE
				--Phí hiện tại cộng thêm phí trả thêm cho tác giả đó
				SET CPR_Fee = CPR_Fee + @Fee
				Where (Select CAST(SUBSTRING(@CurrentDate,1,6) AS int)) = (Select TOP 1 CAST(SUBSTRING(CPR_Fee_Date,1,6) AS int) From COPYRIGHT_FEE Where @ISBN = ISBN AND Writer_ID = @WriterID ORDER BY CPR_Fee_ID desc)
						AND @WriterID = Writer_ID
						AND @ISBN = ISBN

				Update STATISTIC
				Set CPR_Fee = CPR_Fee + @Fee
				Where ((Select CAST(SUBSTRING(@CurrentDate,1,6) AS int)) = (Select TOP 1 CAST(Stat_Code AS int) From STATISTIC Order by ID desc))
				--Commit tran để cập nhật số liệu
				IF(@TranCounter = 0)
					COMMIT TRAN
			End
			--Nếu không cùng thì thực hiện thêm cột đó với giá trị tham số truyền vào tương ứng
			ELSE
			Begin
				INSERT COPYRIGHT_FEE(ISBN, Writer_ID, CPR_Fee)
				VALUES (@ISBN, @WriterID, @Fee)

				IF((Select CAST(SUBSTRING(@CurrentDate,1,6) AS int)) = (Select TOP 1 CAST(Stat_Code AS int) From STATISTIC Order by ID desc))
				Begin
					Update STATISTIC
					Set CPR_Fee = CPR_Fee + @Fee
					Where ((Select CAST(SUBSTRING(@CurrentDate,1,6) AS int)) = (Select TOP 1 CAST(Stat_Code AS int) From STATISTIC Order by ID desc))
				End
				ELSE
				Begin
					INSERT STATISTIC (Revenue, CPR_Fee, Profit)
					VALUES (0,@Fee, @Fee)
				End
				--Commit tran để thêm dữ liệu vào bảng COPYRIGHT_FEE
				IF(@TranCounter = 0)
					COMMIT TRAN
			End
		End TRY
		Begin CATCH
			--Cái này trên cũng đã giải thích r nên không nhắc lại nữa
			IF(@TranCounter = 0)
				ROLLBACK TRAN
			ELSE
			Begin
				IF(XACT_STATE() <> -1)
					ROLLBACK TRAN SavePoint
			End
			Declare @ErrorMessage nvarchar(4000)
			Declare @ErrorSeverity int
			Declare @ErrorState int

			Select @ErrorMessage = ERROR_MESSAGE()
			Select @ErrorSeverity = ERROR_SEVERITY()
			Select @ErrorState = ERROR_STATE()

			RAISERROR  (@ErrorMessage, --Message text
						@ErrorSeverity, --Severity
						@ErrorState --State
						)
		End CATCH
End
GO
/*
Select * From COPYRIGHT_FEE
exec sp_InsertCOPYRIGHT_FEE 9786047364596, 2, 100000
Select * From BOOK
*/
--Tạo trigger để mỗi lần thêm vào REVENUE thì tự động thêm vào STAT
IF(OBJECT_ID(N'trg_InsertSTATfromREVENUE', N'TR') IS NOT NULL)
	DROP TRIGGER trg_InsertSTATfromREVENUE
GO

Create TRIGGER trg_InsertSTATfromREVENUE ON REVENUE
AFTER Insert
AS
Begin
	SET NOCOUNT ON
	--Này quên thuộc như ăn cơm r nên k cần nhắc nữa
	Declare @TranCounter int
	Set @TranCounter = @@TRANCOUNT
	IF(@TranCounter > 0)
		SAVE TRAN SavePoint
	ELSE
	Begin TRAN
		Begin TRY
			--Nếu giá trị Revenue_Date vừa thêm vào bảng REVENUE và đem so với Stat_Code trong bảng STATISTC (nếu có)
			--Nếu cùng giá trị thì sẽ update revenue trong bảng STATISTIC bằng cách tăng revenue hiện tại + gain vừa mới thu đc bên REVENUE
			IF((Select Top 1 CAST(SUBSTRING(Revenue_Date,1,6) AS int) AS 'Code' From REVENUE Order by [Code] desc) = (Select TOP 1 CAST(Stat_Code AS int) AS 'Code' From STATISTIC Order by [Code] desc))
			Begin
				Update STATISTIC
				Set Revenue = Revenue + (Select Top 1 Gain From REVENUE Order by Revenue_ID desc)
				Where (Select Top 1 CAST(SUBSTRING(Revenue_Date,1,6) AS int) AS 'Code' From REVENUE Order by [Code] desc) = (Select TOP 1 CAST(Stat_Code AS int) AS 'Code' From STATISTIC Order by [Code] desc)
				--Này chắc gặp còn nhiều hơn ăn cơm
				IF(@TranCounter = 0)
					COMMIT TRAN
			End
			--Nếu không trùng nhau thì thêm giá trị mới đó với 1 cái Stat_Code mới
			ELSE
			Begin
				INSERT STATISTIC(Revenue, CPR_Fee, Profit)
				VALUES ((Select Top 1 Gain From REVENUE Order by Revenue_ID desc), 0, (Select Top 1 Gain From REVENUE Order by Revenue_ID desc))

				IF(@TranCounter = 0)
					COMMIT TRAN
			End
		End TRY
		Begin CATCH
			IF(@TranCounter = 0)
				ROLLBACK TRAN
			ELSE
			Begin
				IF(XACT_STATE() <> -1)
					ROLLBACK TRAN SavePoint
			End
			Declare @ErrorMessage nvarchar(4000)
			Declare @ErrorSeverity int
			Declare @ErrorState int

			Select @ErrorMessage = ERROR_MESSAGE()
			Select @ErrorSeverity = ERROR_SEVERITY()
			Select @ErrorState = ERROR_STATE()

			RAISERROR  (@ErrorMessage, --Message text
						@ErrorSeverity, --Severity
						@ErrorState --State
						)
		End CATCH
End
GO
/*
Select * From WRITER
Select * From BOOK
Select * From STATISTIC
*/
IF(OBJECT_ID(N'trg_UpdateSTAT', N'TR') IS NOT NULL)
	DROP TRIGGER trg_UpdateSTAT
Go

Create TRIGGER trg_UpdateSTAT ON STATISTIC
AFTER UPDATE
AS
Begin
	SET NOCOUNT ON
	Declare @TranCounter int
	Set @TranCounter = @@TRANCOUNT
	IF(@TranCounter > 0)
		SAVE TRAN SavePoint
	ELSE
	Begin TRAN
		Begin TRY
			Declare @LastID int
			Set @LastID = (Select MAX(ID) From STATISTIC)
			IF(@LastID = 1)
			Begin
				Update STATISTIC
				Set Profit = Revenue - CPR_Fee,
					Growth = 0
				Where @LastID = ID

				IF(@TranCounter = 0)
					COMMIT TRAN
			End
			ELSE
			Begin
				Update STATISTIC
				Set Profit = Revenue - CPR_Fee
				Where @LastID = ID

				Update STATISTIC
				Set Growth =  (Select CAST((((Select Profit From STATISTIC Where @LastID = ID) / (Select Profit From STATISTIC Where @LastID - 1 = ID)) * 100) - 100 AS decimal(18,3)))

				IF(@TranCounter = 0)
					COMMIT TRAN
			End
		End TRY
		Begin CATCH
			IF(@TranCounter = 0)
				ROLLBACK TRAn
			ELSE
			Begin
				IF(XACT_STATE() <> -1)
					ROLLBACK TRAN SavePoint
			End
			Declare @ErrorMessage nvarchar(4000)
			Declare @ErrorSeverity int
			Declare @ErrorState int

			Select @ErrorMessage = ERROR_MESSAGE()
			Select @ErrorSeverity = ERROR_SEVERITY()
			Select @ErrorState = ERROR_STATE()

			RAISERROR  (@ErrorMessage, --Message text
						@ErrorSeverity, --Severity
						@ErrorState --State
						)
		End CATCH
End
GO
/*
IF(OBJECT_ID(N'trg_InsertSTATfromCPR_FEE', N'TR') IS NOT NULL)
	DROP TRIGGER trg_InsertSTATfromCPR_FEE
GO

Create TRIGGER trg_InsertSTATfromCPR_FEE ON COPYRIGHT_FEE
AFTER INSERT, UPDATE
AS
Begin
	SET NOCOUNT ON
	Declare @TranCouter int
	Set @TranCouter = @@TRANCOUNT
	IF(@TranCouter > 0)
		SAVE TRAN SavePoint
	ELSE
	Begin TRAN
		Begin TRY
			IF((Select TOP 1 CAST(SUBSTRING(CPR_Fee_Date,1,6) AS int) From inserted Order by CPR_Fee_ID desc) = (Select TOP 1 CAST(SUBSTRING(Stat_Code,1,6) AS int) From STATISTIC Order by ID desc))
			Begin
				Update STATISTIC
				Set CPR_Fee = CPR_Fee + (Select Top 1 CPR_Fee From inserted Order by CPR_Fee_ID desc)
				Where (Select Top 1 CAST(SUBSTRING(CPR_Fee_Date,1,6) AS int) From inserted Order by CPR_Fee_ID desc) = (Select TOP 1 CAST(SUBSTRING(Stat_Code,1,6) AS int) From STATISTIC Order by ID desc)

				IF(@TranCouter = 0)
					COMMIT TRAN
			End
			ELSE
			Begin
				INSERT STATISTIC (CPR_Fee)
				VALUES ((Select Top 1 CPR_Fee From inserted Order by CPR_Fee_ID desc))
				
				IF(@TranCouter = 0)
					COMMIT TRAN
			End
		End TRY
		Begin CATCH
			IF(@TranCouter = 0)
				ROLLBACK TRAN
			ELSE
			Begin
				IF(XACT_STATE() <> -1)
					ROLLBACK TRAN SavePoint
			End
			Declare @ErrorMessage nvarchar(4000)
			Declare @ErrorSeverity int
			Declare @ErrorState int

			Select @ErrorMessage = ERROR_MESSAGE()
			Select @ErrorSeverity = ERROR_SEVERITY()
			Select @ErrorState = ERROR_STATE()

			RAISERROR  (@ErrorMessage, --Message text
						@ErrorSeverity, --Severity
						@ErrorState --State
						)
		End CATCH
End
GO
*/

/*
SELECT
    table_name
FROM
    INFORMATION_SCHEMA.TABLES T
WHERE
    T.TABLE_CATALOG = 'Library_Management_Biden' AND
    T.TABLE_TYPE = 'BASE TABLE' AND
    EXISTS (
        SELECT *
        FROM INFORMATION_SCHEMA.COLUMNS C
        WHERE
            C.TABLE_CATALOG = T.TABLE_CATALOG AND
            C.TABLE_SCHEMA = T.TABLE_SCHEMA AND
            C.TABLE_NAME = T.TABLE_NAME AND
            C.COLUMN_NAME = 'InStock_Mount')
*/

/*
Declare @date char(8)
Set @date = Convert(char(8),getdate(),112)
IF(Select CAST(SUBSTRING(@date,1,6) AS int)) = (Select TOP 1 CAST(SUBSTRING(CPR_Fee_Date,1,6) AS int) From COPYRIGHT_FEE)
	PRINT N'Được'
Else
	PRINT N'Không Được'
*/

