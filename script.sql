USE [master]
GO
/****** Object:  Database [Yame Shop]    Script Date: 6/16/2020 3:03:07 AM ******/
CREATE DATABASE [Yame Shop]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Yame Shop', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQL\MSSQL\DATA\Yame Shop.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Yame Shop_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQL\MSSQL\DATA\Yame Shop_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Yame Shop] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Yame Shop].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Yame Shop] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Yame Shop] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Yame Shop] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Yame Shop] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Yame Shop] SET ARITHABORT OFF 
GO
ALTER DATABASE [Yame Shop] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Yame Shop] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Yame Shop] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Yame Shop] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Yame Shop] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Yame Shop] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Yame Shop] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Yame Shop] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Yame Shop] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Yame Shop] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Yame Shop] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Yame Shop] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Yame Shop] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Yame Shop] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Yame Shop] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Yame Shop] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Yame Shop] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Yame Shop] SET RECOVERY FULL 
GO
ALTER DATABASE [Yame Shop] SET  MULTI_USER 
GO
ALTER DATABASE [Yame Shop] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Yame Shop] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Yame Shop] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Yame Shop] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Yame Shop] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'Yame Shop', N'ON'
GO
ALTER DATABASE [Yame Shop] SET QUERY_STORE = OFF
GO
USE [Yame Shop]
GO
/****** Object:  Table [dbo].[chi_nhanh]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[chi_nhanh](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_cn] [varchar](max) NOT NULL,
	[ten_cn] [nvarchar](500) NOT NULL,
	[dia_chi] [nvarchar](500) NOT NULL,
	[sdt] [varchar](11) NOT NULL,
 CONSTRAINT [PK_chi_nhanh] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[chuc_nang]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[chuc_nang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_chuc_nang] [varchar](max) NOT NULL,
	[ten_chuc_nang] [nvarchar](500) NOT NULL,
 CONSTRAINT [PK_chuc_nang] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[chuc_vu]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[chuc_vu](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_cv] [varchar](max) NOT NULL,
	[ten_cv] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_Table1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ct_hoa_don]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ct_hoa_don](
	[ma_hd] [int] NOT NULL,
	[ma_sp] [int] NOT NULL,
	[so_luong] [int] NOT NULL,
	[don_gia] [float] NOT NULL,
	[tong_tien] [float] NOT NULL,
 CONSTRAINT [PK_cthd] PRIMARY KEY CLUSTERED 
(
	[ma_hd] ASC,
	[ma_sp] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ct_phieu_nhap]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ct_phieu_nhap](
	[ma_pn] [int] NOT NULL,
	[ma_sp] [int] NOT NULL,
	[so_luong_nhap] [int] NOT NULL,
	[don_gia] [float] NOT NULL,
	[tong_tien] [float] NOT NULL,
 CONSTRAINT [PK_Table_1_3] PRIMARY KEY CLUSTERED 
(
	[ma_pn] ASC,
	[ma_sp] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[danh_muc_sp]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[danh_muc_sp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_dm] [varchar](max) NOT NULL,
	[ten_dm] [nvarchar](500) NOT NULL,
 CONSTRAINT [PK_Table4] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_don]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hoa_don](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_hd] [varchar](max) NOT NULL,
	[ma_cn] [int] NOT NULL,
	[ma_nv] [int] NOT NULL,
	[ma_kh] [int] NOT NULL,
	[ten_kh] [nvarchar](500) NULL,
	[ngay_tao] [date] NOT NULL,
	[giam_gia] [float] NOT NULL,
	[tong_tien] [float] NOT NULL,
 CONSTRAINT [PK_Table_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[khach_hang]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[khach_hang](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_kh] [varchar](max) NOT NULL,
	[ten_kh] [nvarchar](500) NOT NULL,
	[sdt] [varchar](11) NULL,
	[dia_chi] [nvarchar](max) NULL,
	[email] [varchar](500) NULL,
	[tong_tien] [float] NOT NULL,
 CONSTRAINT [PK_Table_1_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nguoi_dung]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nguoi_dung](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[tai_khoan] [nvarchar](50) NOT NULL,
	[mat_khau] [varchar](max) NOT NULL,
	[ten] [nvarchar](100) NOT NULL,
	[email] [varchar](100) NOT NULL,
	[ma_cv] [int] NOT NULL,
	[ma_cn] [int] NOT NULL,
	[sdt] [varchar](11) NOT NULL,
	[dia_chi] [nvarchar](500) NOT NULL,
	[gioi_tinh] [nvarchar](10) NOT NULL,
	[luong] [decimal](18, 0) NOT NULL,
 CONSTRAINT [PK_Table_1_4] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nha_cung_cap]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nha_cung_cap](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_ncc] [varchar](max) NOT NULL,
	[ten_ncc] [nvarchar](500) NOT NULL,
	[email] [varchar](100) NOT NULL,
	[sdt] [varchar](11) NOT NULL,
	[dia_chi] [nvarchar](500) NOT NULL,
	[tong_tien_nhap] [float] NOT NULL,
 CONSTRAINT [PK_nha_cung_cap] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[phan_quyen]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phan_quyen](
	[ma_cv] [int] NOT NULL,
	[ma_chuc_nang] [int] NOT NULL,
 CONSTRAINT [PK_phan_quyen] PRIMARY KEY CLUSTERED 
(
	[ma_cv] ASC,
	[ma_chuc_nang] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[phieu_nhap]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phieu_nhap](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_pn] [varchar](max) NOT NULL,
	[ma_nv] [int] NOT NULL,
	[ma_ncc] [int] NOT NULL,
	[ma_cn] [int] NOT NULL,
	[ngay_nhap] [date] NOT NULL,
	[tong_tien] [float] NOT NULL,
 CONSTRAINT [PK_Table_1_2] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[san_pham]    Script Date: 6/16/2020 3:03:08 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[san_pham](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ma_sp] [varchar](max) NOT NULL,
	[so_luong] [int] NOT NULL,
	[ten_sp] [nvarchar](500) NOT NULL,
	[kich_thuoc] [nvarchar](100) NOT NULL,
	[hinh_anh] [nvarchar](max) NOT NULL,
	[mo_ta] [nvarchar](max) NULL,
	[gia_ban] [float] NOT NULL,
	[gia_nhap] [float] NOT NULL,
	[ton_kho] [int] NOT NULL,
	[ma_dm] [int] NOT NULL,
	[ngay_nhap] [date] NOT NULL,
 CONSTRAINT [PK_Table_2] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[chi_nhanh] ON 

INSERT [dbo].[chi_nhanh] ([id], [ma_cn], [ten_cn], [dia_chi], [sdt]) VALUES (1, N'Q10', N'YaMe Q10', N'286, 3 tháng 2, P.12, Q.10, TP.HCM', N'7307 1441')
INSERT [dbo].[chi_nhanh] ([id], [ma_cn], [ten_cn], [dia_chi], [sdt]) VALUES (2, N'Q5', N'YaMe Q5', N'190, Nguyễn Trãi, P.3, Q.5, TP.HCM', N'7307 1441')
INSERT [dbo].[chi_nhanh] ([id], [ma_cn], [ten_cn], [dia_chi], [sdt]) VALUES (3, N'Q6', N'YaMe Q6', N'102 Hậu Giang, Q.6, TP.HCM', N'7307 1441')
INSERT [dbo].[chi_nhanh] ([id], [ma_cn], [ten_cn], [dia_chi], [sdt]) VALUES (4, N'Q7', N'YaMe Q7', N'323 Huỳnh Tấn Phát, Q.7, TP.HCM', N'7307 1441')
INSERT [dbo].[chi_nhanh] ([id], [ma_cn], [ten_cn], [dia_chi], [sdt]) VALUES (5, N'Q9', N'YaMe Q9', N'200 Lê Văn Việt P.Tăng Nhơn Phú B, Q.9, TP.HCM', N'7307 1441')
SET IDENTITY_INSERT [dbo].[chi_nhanh] OFF
GO
SET IDENTITY_INSERT [dbo].[chuc_nang] ON 

INSERT [dbo].[chuc_nang] ([id], [ma_chuc_nang], [ten_chuc_nang]) VALUES (1, N'CN01', N'Trang chủ')
INSERT [dbo].[chuc_nang] ([id], [ma_chuc_nang], [ten_chuc_nang]) VALUES (2, N'CN02', N'Cơ cấu')
INSERT [dbo].[chuc_nang] ([id], [ma_chuc_nang], [ten_chuc_nang]) VALUES (3, N'CN03', N'Sản phẩm')
INSERT [dbo].[chuc_nang] ([id], [ma_chuc_nang], [ten_chuc_nang]) VALUES (4, N'CN04', N'Giao dịch')
INSERT [dbo].[chuc_nang] ([id], [ma_chuc_nang], [ten_chuc_nang]) VALUES (5, N'CN05', N'Hóa đơn')
INSERT [dbo].[chuc_nang] ([id], [ma_chuc_nang], [ten_chuc_nang]) VALUES (6, N'CN06', N'Đối tác')
INSERT [dbo].[chuc_nang] ([id], [ma_chuc_nang], [ten_chuc_nang]) VALUES (7, N'CN07', N'Báo cáo')
INSERT [dbo].[chuc_nang] ([id], [ma_chuc_nang], [ten_chuc_nang]) VALUES (8, N'CN07', N'Bán hàng')
SET IDENTITY_INSERT [dbo].[chuc_nang] OFF
GO
SET IDENTITY_INSERT [dbo].[chuc_vu] ON 

INSERT [dbo].[chuc_vu] ([id], [ma_cv], [ten_cv]) VALUES (1, N'CV01', N'Quản lý')
INSERT [dbo].[chuc_vu] ([id], [ma_cv], [ten_cv]) VALUES (2, N'CV02', N'Nhân viên bán hàng')
INSERT [dbo].[chuc_vu] ([id], [ma_cv], [ten_cv]) VALUES (3, N'CV03', N'Kế toán')
INSERT [dbo].[chuc_vu] ([id], [ma_cv], [ten_cv]) VALUES (4, N'CV04', N'Thủ kho')
SET IDENTITY_INSERT [dbo].[chuc_vu] OFF
GO
INSERT [dbo].[ct_hoa_don] ([ma_hd], [ma_sp], [so_luong], [don_gia], [tong_tien]) VALUES (2, 5, 2, 295000, 590000)
INSERT [dbo].[ct_hoa_don] ([ma_hd], [ma_sp], [so_luong], [don_gia], [tong_tien]) VALUES (2, 9, 2, 225000, 450000)
INSERT [dbo].[ct_hoa_don] ([ma_hd], [ma_sp], [so_luong], [don_gia], [tong_tien]) VALUES (3, 1, 1, 425000, 425000)
INSERT [dbo].[ct_hoa_don] ([ma_hd], [ma_sp], [so_luong], [don_gia], [tong_tien]) VALUES (3, 2, 1, 425000, 425000)
INSERT [dbo].[ct_hoa_don] ([ma_hd], [ma_sp], [so_luong], [don_gia], [tong_tien]) VALUES (3, 3, 1, 350000, 350000)
INSERT [dbo].[ct_hoa_don] ([ma_hd], [ma_sp], [so_luong], [don_gia], [tong_tien]) VALUES (3, 4, 1, 115000, 115000)
INSERT [dbo].[ct_hoa_don] ([ma_hd], [ma_sp], [so_luong], [don_gia], [tong_tien]) VALUES (3, 5, 1, 295000, 295000)
GO
INSERT [dbo].[ct_phieu_nhap] ([ma_pn], [ma_sp], [so_luong_nhap], [don_gia], [tong_tien]) VALUES (1, 1, 10, 123123, 1231230)
INSERT [dbo].[ct_phieu_nhap] ([ma_pn], [ma_sp], [so_luong_nhap], [don_gia], [tong_tien]) VALUES (1, 2, 20, 123123, 2462460)
GO
SET IDENTITY_INSERT [dbo].[danh_muc_sp] ON 

INSERT [dbo].[danh_muc_sp] ([id], [ma_dm], [ten_dm]) VALUES (1, N'quan1', N'Quần dài')
INSERT [dbo].[danh_muc_sp] ([id], [ma_dm], [ten_dm]) VALUES (2, N'quan2', N'Quần short')
INSERT [dbo].[danh_muc_sp] ([id], [ma_dm], [ten_dm]) VALUES (3, N'ao1', N'Áo thun')
INSERT [dbo].[danh_muc_sp] ([id], [ma_dm], [ten_dm]) VALUES (4, N'ao2', N'Áo khoác')
INSERT [dbo].[danh_muc_sp] ([id], [ma_dm], [ten_dm]) VALUES (5, N'ao3', N'Áo sơ mi')
INSERT [dbo].[danh_muc_sp] ([id], [ma_dm], [ten_dm]) VALUES (6, N'ao4', N'Áo len')
SET IDENTITY_INSERT [dbo].[danh_muc_sp] OFF
GO
SET IDENTITY_INSERT [dbo].[hoa_don] ON 

INSERT [dbo].[hoa_don] ([id], [ma_hd], [ma_cn], [ma_nv], [ma_kh], [ten_kh], [ngay_tao], [giam_gia], [tong_tien]) VALUES (2, N'HD002', 1, 1, 1, N'Nguyễn Nhật Cần', CAST(N'2020-09-06' AS Date), 0, 1040000)
INSERT [dbo].[hoa_don] ([id], [ma_hd], [ma_cn], [ma_nv], [ma_kh], [ten_kh], [ngay_tao], [giam_gia], [tong_tien]) VALUES (3, N'HD003', 1, 2, 2, N'Lê Minh Luân', CAST(N'2020-06-14' AS Date), 0, 1610000)
SET IDENTITY_INSERT [dbo].[hoa_don] OFF
GO
SET IDENTITY_INSERT [dbo].[khach_hang] ON 

INSERT [dbo].[khach_hang] ([id], [ma_kh], [ten_kh], [sdt], [dia_chi], [email], [tong_tien]) VALUES (1, N'KH001', N'Nguyễn Nhật Cần', N'0392054249', N'35 D5, P.25, Q.Bình Thạnh, TP.HCM', N'nncanc1@gmail.com', 1000000)
INSERT [dbo].[khach_hang] ([id], [ma_kh], [ten_kh], [sdt], [dia_chi], [email], [tong_tien]) VALUES (2, N'KH002', N'Lê Minh Luân', N'0834694786', N'D5, P25, Q.Bình Thạnh, TP.HCM', N'luamml@gmail.com', 4000000)
INSERT [dbo].[khach_hang] ([id], [ma_kh], [ten_kh], [sdt], [dia_chi], [email], [tong_tien]) VALUES (3, N'KH003', N'aaa', N'11111111111', N'1', N'1@gmail.com', 0)
INSERT [dbo].[khach_hang] ([id], [ma_kh], [ten_kh], [sdt], [dia_chi], [email], [tong_tien]) VALUES (4, N'KH004', N'aaa', N'11111111111', N'1', N'1@gmail.com', 0)
INSERT [dbo].[khach_hang] ([id], [ma_kh], [ten_kh], [sdt], [dia_chi], [email], [tong_tien]) VALUES (5, N'KH005', N'q', N'1111111111', N'1', N'1@gmail.com', 0)
SET IDENTITY_INSERT [dbo].[khach_hang] OFF
GO
SET IDENTITY_INSERT [dbo].[nguoi_dung] ON 

INSERT [dbo].[nguoi_dung] ([id], [tai_khoan], [mat_khau], [ten], [email], [ma_cv], [ma_cn], [sdt], [dia_chi], [gioi_tinh], [luong]) VALUES (1, N'admin', N'6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', N'admin', N'admin@gmail.com', 1, 1, N'1234567890', N'ho chi minh', N'nam', CAST(10000000 AS Decimal(18, 0)))
INSERT [dbo].[nguoi_dung] ([id], [tai_khoan], [mat_khau], [ten], [email], [ma_cv], [ma_cn], [sdt], [dia_chi], [gioi_tinh], [luong]) VALUES (2, N'banhang', N'6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', N'banhang', N'banhang@gmail.com', 2, 1, N'1234567890', N'ho chi minh', N'nam', CAST(5000000 AS Decimal(18, 0)))
INSERT [dbo].[nguoi_dung] ([id], [tai_khoan], [mat_khau], [ten], [email], [ma_cv], [ma_cn], [sdt], [dia_chi], [gioi_tinh], [luong]) VALUES (5, N'ketoan', N'6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', N'ketoan', N'ketoan@gmail.com', 3, 1, N'1234567890', N'ho chi minh', N'nam', CAST(8000000 AS Decimal(18, 0)))
INSERT [dbo].[nguoi_dung] ([id], [tai_khoan], [mat_khau], [ten], [email], [ma_cv], [ma_cn], [sdt], [dia_chi], [gioi_tinh], [luong]) VALUES (7, N'thukho', N'6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', N'thukho', N'thukho@gmail.com', 4, 1, N'1234567890', N'ho chi minh', N'nam', CAST(5000000 AS Decimal(18, 0)))
INSERT [dbo].[nguoi_dung] ([id], [tai_khoan], [mat_khau], [ten], [email], [ma_cv], [ma_cn], [sdt], [dia_chi], [gioi_tinh], [luong]) VALUES (8, N'test', N'6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b', N'test', N'1', 1, 1, N'1234567890', N'1', N'nam', CAST(40000 AS Decimal(18, 0)))
SET IDENTITY_INSERT [dbo].[nguoi_dung] OFF
GO
SET IDENTITY_INSERT [dbo].[nha_cung_cap] ON 

INSERT [dbo].[nha_cung_cap] ([id], [ma_ncc], [ten_ncc], [email], [sdt], [dia_chi], [tong_tien_nhap]) VALUES (1, N'NCC01', N'Xưởng Bỏ Sỉ Quần Áo Bán Hàng Online Giá Tận Gốc', N'mydung@gmail.com', N'0905940207', N'117 ĐIỆN BIÊN PHỦ - PHƯỜNG 15 - QUẬN BÌNH THẠNH - TP. HỒ CHÍ MINH', 9000000)
INSERT [dbo].[nha_cung_cap] ([id], [ma_ncc], [ten_ncc], [email], [sdt], [dia_chi], [tong_tien_nhap]) VALUES (2, N'NCC02', N'Xưởng may quần áo giá sỉ tphcm - Nam DON Group', N'namdongroup@gmail.com', N'0937470290', N'A29 bis, Nguyễn Văn Quá, P. Đông Hưng Thuận, Quận 12, TP. Hồ Chí Minh', 10000000)
INSERT [dbo].[nha_cung_cap] ([id], [ma_ncc], [ten_ncc], [email], [sdt], [dia_chi], [tong_tien_nhap]) VALUES (3, N'NCC03', N'Áo Quần Giá Sỉ', N'aoquangiasi@gmail.com', N'01265466020', N'373/33 Lý Thường Kiệt, P.9, Q. Tân Bình ', 8000000)
SET IDENTITY_INSERT [dbo].[nha_cung_cap] OFF
GO
INSERT [dbo].[phan_quyen] ([ma_cv], [ma_chuc_nang]) VALUES (1, 1)
INSERT [dbo].[phan_quyen] ([ma_cv], [ma_chuc_nang]) VALUES (1, 2)
INSERT [dbo].[phan_quyen] ([ma_cv], [ma_chuc_nang]) VALUES (1, 3)
INSERT [dbo].[phan_quyen] ([ma_cv], [ma_chuc_nang]) VALUES (1, 4)
INSERT [dbo].[phan_quyen] ([ma_cv], [ma_chuc_nang]) VALUES (1, 5)
INSERT [dbo].[phan_quyen] ([ma_cv], [ma_chuc_nang]) VALUES (1, 6)
INSERT [dbo].[phan_quyen] ([ma_cv], [ma_chuc_nang]) VALUES (1, 7)
INSERT [dbo].[phan_quyen] ([ma_cv], [ma_chuc_nang]) VALUES (1, 8)
INSERT [dbo].[phan_quyen] ([ma_cv], [ma_chuc_nang]) VALUES (2, 1)
INSERT [dbo].[phan_quyen] ([ma_cv], [ma_chuc_nang]) VALUES (2, 8)
GO
SET IDENTITY_INSERT [dbo].[phieu_nhap] ON 

INSERT [dbo].[phieu_nhap] ([id], [ma_pn], [ma_nv], [ma_ncc], [ma_cn], [ngay_nhap], [tong_tien]) VALUES (1, N'PN001', 1, 1, 1, CAST(N'2020-06-16' AS Date), 3693690)
SET IDENTITY_INSERT [dbo].[phieu_nhap] OFF
GO
SET IDENTITY_INSERT [dbo].[san_pham] ON 

INSERT [dbo].[san_pham] ([id], [ma_sp], [so_luong], [ten_sp], [kich_thuoc], [hinh_anh], [mo_ta], [gia_ban], [gia_nhap], [ton_kho], [ma_dm], [ngay_nhap]) VALUES (1, N'SP001', 55, N'Quần Jean Slimfit Y2010 B35', N'31, 32, 33', N'src/images/SanPham/Quần Jean Slimfit Y2010 B35.png', N'Chất liệu: Jean Cotton - Thành phần: 98% cotton 2% spandex - Độ bền cao - Mặc mát, rất thoải mái.', 425000, 123123, 65, 1, CAST(N'2020-05-20' AS Date))
INSERT [dbo].[san_pham] ([id], [ma_sp], [so_luong], [ten_sp], [kich_thuoc], [hinh_anh], [mo_ta], [gia_ban], [gia_nhap], [ton_kho], [ma_dm], [ngay_nhap]) VALUES (2, N'SP002', 156, N'Quần Jean Slimfit Y2010 B36', N'29, 30, 31, 32', N'src/images/SanPham/Quần Jean Slimfit Y2010 B36.png', N'Chất liệu: Jean Cotton - Thành phần: 98% cotton 2% spandex - Độ bền cao - Mặc mát, rất thoải mái.', 425000, 123123, 186, 1, CAST(N'2020-05-20' AS Date))
INSERT [dbo].[san_pham] ([id], [ma_sp], [so_luong], [ten_sp], [kich_thuoc], [hinh_anh], [mo_ta], [gia_ban], [gia_nhap], [ton_kho], [ma_dm], [ngay_nhap]) VALUES (3, N'SP003', 39, N'Quần Jogger Thun Y2010 E03', N'M, L, XL ', N'src/images/SanPham/Quần Jogger Thun Y2010 E03.png', N'Chất liệu: Vảy cá chéo - Thành phần: 95%cotton 5%spandex - Co dãn 4 chiều nên tạo được sự thoải mái khi mặc - Vải thấm hút mồ hôi tốt, thoáng khí', 350000, 213123, 49, 1, CAST(N'2020-05-20' AS Date))
INSERT [dbo].[san_pham] ([id], [ma_sp], [so_luong], [ten_sp], [kich_thuoc], [hinh_anh], [mo_ta], [gia_ban], [gia_nhap], [ton_kho], [ma_dm], [ngay_nhap]) VALUES (4, N'SP004', -2, N'Quần Short Kaki Ma Bư ST09', N'31, 32, ', N'src/images/SanPham/Quần Short Kaki Ma Bư ST09.png', N'Quần xịn', 115000, 123123, 8, 2, CAST(N'2020-05-20' AS Date))
INSERT [dbo].[san_pham] ([id], [ma_sp], [so_luong], [ten_sp], [kich_thuoc], [hinh_anh], [mo_ta], [gia_ban], [gia_nhap], [ton_kho], [ma_dm], [ngay_nhap]) VALUES (5, N'SP005', 8, N'Quần Short Jean Y2010 B05', N'31, 32, 33', N'src/images/SanPham/Quần Short Jean Y2010 B05.png', N'Chất liệu: Jean - Thành phần: Cotton Duck - Độ bền rất cao. - Mặc rất thoải mái. - Giặt qua lần đầu trước khi mặc. Công đoạn giặt lần đầu này sẽ làm giảm bớt bụi vải cũng như các chất hóa học còn đọng lại trên bề mặt vải.- Hãy lộn mặt trái của sản phẩm để giặt. Hành động này sẽ giữ màu tốt hơn cho trang phục làm từ vải jeans mà bạn đang sở hữu.', 295000, 123123, 17, 2, CAST(N'2020-05-20' AS Date))
INSERT [dbo].[san_pham] ([id], [ma_sp], [so_luong], [ten_sp], [kich_thuoc], [hinh_anh], [mo_ta], [gia_ban], [gia_nhap], [ton_kho], [ma_dm], [ngay_nhap]) VALUES (6, N'SP006', 10, N'Áo Thun Cổ tròn Y2010 E13', N'M, L, XL', N'src/images/SanPham/Áo Thun Cổ tròn Y2010 E13.png', N'Chất liệu: Cotton 2C - Thành phần: 100% Cotton - Co dãn 2 chiều - Thấm hút mồ hôi tốt mang lại cảm giác thoáng mát - Giặt tay để sản phẩm giữ được độ bền cao - Ủi sản phẩm bằng bàn ủi hơi nước hoặc ủi khi sản phẩm còn ẩm để dễ dàng làm phẳng', 150000, 11111, 20, 3, CAST(N'2020-05-20' AS Date))
INSERT [dbo].[san_pham] ([id], [ma_sp], [so_luong], [ten_sp], [kich_thuoc], [hinh_anh], [mo_ta], [gia_ban], [gia_nhap], [ton_kho], [ma_dm], [ngay_nhap]) VALUES (7, N'SP007', 15, N'Áo Thun Nam Y2010 BD-B06', N'M, L, XL', N'src/images/SanPham/Áo Thun Nam Y2010 BD-B06.png', N'Chất liệu: Cotton 2 chiều - Thành phần: 100% Cotton - Co dãn 2 chiều - Thấm hút mồ hôi tốt mang lại cảm giác thoáng mát - Giặt tay để sản phẩm giữ được độ bền cao - Ủi sản phẩm bằng bàn ủi hơi nước hoặc ủi khi sản phẩm còn ẩm để dễ dàng làm phẳng', 185000, 1111111, 15, 4, CAST(N'2020-05-20' AS Date))
INSERT [dbo].[san_pham] ([id], [ma_sp], [so_luong], [ten_sp], [kich_thuoc], [hinh_anh], [mo_ta], [gia_ban], [gia_nhap], [ton_kho], [ma_dm], [ngay_nhap]) VALUES (8, N'SP008', 10, N'Áo Khoác Dù Có Nón Adachi A08', N'M, L, XL', N'src/images/SanPham/Áo Khoác Dù Có Nón Adachi A08.png', N'Chất liệu: Dù poly 100% TRƯỢT NƯỚC - Kỹ thuật in mờ - Dây kéo YKK - Nhiều túi tiện dụng - Vải dù bền, dai và nhẹ - Ưu điểm vượt trội là thoáng khí', 215000, 1111, 20, 4, CAST(N'2020-05-20' AS Date))
INSERT [dbo].[san_pham] ([id], [ma_sp], [so_luong], [ten_sp], [kich_thuoc], [hinh_anh], [mo_ta], [gia_ban], [gia_nhap], [ton_kho], [ma_dm], [ngay_nhap]) VALUES (9, N'SP009', 20, N'Sơ Mi Nam No Style TD KM16', N'M, L, XL', N'src/images/SanPham/Sơ Mi Nam No Style TD KM16.png', N'Áo đẹp', 225000, 1, 20, 5, CAST(N'2020-05-20' AS Date))
INSERT [dbo].[san_pham] ([id], [ma_sp], [so_luong], [ten_sp], [kich_thuoc], [hinh_anh], [mo_ta], [gia_ban], [gia_nhap], [ton_kho], [ma_dm], [ngay_nhap]) VALUES (10, N'SP010', 62, N'Sơ Mi Nam Y2010 TN G01', N'M, L, XL', N'src/images/SanPham/Sơ Mi Nam Y2010 TN G01.png', N'Chất liệu: Kate - Thành phần : 100% poly - Khả năng hút ẩm tốt - Độ bền vải cao', 225000, 123, 64, 5, CAST(N'2020-05-20' AS Date))
SET IDENTITY_INSERT [dbo].[san_pham] OFF
GO
ALTER TABLE [dbo].[ct_hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_ct_hoa_don_hoa_don] FOREIGN KEY([ma_hd])
REFERENCES [dbo].[hoa_don] ([id])
GO
ALTER TABLE [dbo].[ct_hoa_don] CHECK CONSTRAINT [FK_ct_hoa_don_hoa_don]
GO
ALTER TABLE [dbo].[ct_hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_ct_hoa_don_san_pham] FOREIGN KEY([ma_sp])
REFERENCES [dbo].[san_pham] ([id])
GO
ALTER TABLE [dbo].[ct_hoa_don] CHECK CONSTRAINT [FK_ct_hoa_don_san_pham]
GO
ALTER TABLE [dbo].[ct_phieu_nhap]  WITH CHECK ADD  CONSTRAINT [FK_ct_phieu_nhap_phieu_nhap] FOREIGN KEY([ma_pn])
REFERENCES [dbo].[phieu_nhap] ([id])
GO
ALTER TABLE [dbo].[ct_phieu_nhap] CHECK CONSTRAINT [FK_ct_phieu_nhap_phieu_nhap]
GO
ALTER TABLE [dbo].[ct_phieu_nhap]  WITH CHECK ADD  CONSTRAINT [FK_ct_phieu_nhap_san_pham1] FOREIGN KEY([ma_sp])
REFERENCES [dbo].[san_pham] ([id])
GO
ALTER TABLE [dbo].[ct_phieu_nhap] CHECK CONSTRAINT [FK_ct_phieu_nhap_san_pham1]
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_hoa_don_chi_nhanh1] FOREIGN KEY([ma_cn])
REFERENCES [dbo].[chi_nhanh] ([id])
GO
ALTER TABLE [dbo].[hoa_don] CHECK CONSTRAINT [FK_hoa_don_chi_nhanh1]
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_hoa_don_khach_hang] FOREIGN KEY([ma_kh])
REFERENCES [dbo].[khach_hang] ([id])
GO
ALTER TABLE [dbo].[hoa_don] CHECK CONSTRAINT [FK_hoa_don_khach_hang]
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_hoa_don_nguoi_dung] FOREIGN KEY([ma_nv])
REFERENCES [dbo].[nguoi_dung] ([id])
GO
ALTER TABLE [dbo].[hoa_don] CHECK CONSTRAINT [FK_hoa_don_nguoi_dung]
GO
ALTER TABLE [dbo].[nguoi_dung]  WITH CHECK ADD  CONSTRAINT [FK_nguoi_dung_chi_nhanh] FOREIGN KEY([ma_cn])
REFERENCES [dbo].[chi_nhanh] ([id])
GO
ALTER TABLE [dbo].[nguoi_dung] CHECK CONSTRAINT [FK_nguoi_dung_chi_nhanh]
GO
ALTER TABLE [dbo].[nguoi_dung]  WITH CHECK ADD  CONSTRAINT [FK_nguoi_dung_chuc_vu] FOREIGN KEY([ma_cv])
REFERENCES [dbo].[chuc_vu] ([id])
GO
ALTER TABLE [dbo].[nguoi_dung] CHECK CONSTRAINT [FK_nguoi_dung_chuc_vu]
GO
ALTER TABLE [dbo].[phan_quyen]  WITH CHECK ADD  CONSTRAINT [FK_phan_quyen_chuc_nang] FOREIGN KEY([ma_chuc_nang])
REFERENCES [dbo].[chuc_nang] ([id])
GO
ALTER TABLE [dbo].[phan_quyen] CHECK CONSTRAINT [FK_phan_quyen_chuc_nang]
GO
ALTER TABLE [dbo].[phan_quyen]  WITH CHECK ADD  CONSTRAINT [FK_phan_quyen_chuc_vu] FOREIGN KEY([ma_cv])
REFERENCES [dbo].[chuc_vu] ([id])
GO
ALTER TABLE [dbo].[phan_quyen] CHECK CONSTRAINT [FK_phan_quyen_chuc_vu]
GO
ALTER TABLE [dbo].[phieu_nhap]  WITH CHECK ADD  CONSTRAINT [FK_phieu_nhap_chi_nhanh] FOREIGN KEY([ma_cn])
REFERENCES [dbo].[chi_nhanh] ([id])
GO
ALTER TABLE [dbo].[phieu_nhap] CHECK CONSTRAINT [FK_phieu_nhap_chi_nhanh]
GO
ALTER TABLE [dbo].[phieu_nhap]  WITH CHECK ADD  CONSTRAINT [FK_phieu_nhap_nguoi_dung] FOREIGN KEY([ma_nv])
REFERENCES [dbo].[nguoi_dung] ([id])
GO
ALTER TABLE [dbo].[phieu_nhap] CHECK CONSTRAINT [FK_phieu_nhap_nguoi_dung]
GO
ALTER TABLE [dbo].[phieu_nhap]  WITH CHECK ADD  CONSTRAINT [FK_phieu_nhap_nha_cung_cap] FOREIGN KEY([ma_ncc])
REFERENCES [dbo].[nha_cung_cap] ([id])
GO
ALTER TABLE [dbo].[phieu_nhap] CHECK CONSTRAINT [FK_phieu_nhap_nha_cung_cap]
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK_san_pham_danh_muc_sp] FOREIGN KEY([ma_dm])
REFERENCES [dbo].[danh_muc_sp] ([id])
GO
ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK_san_pham_danh_muc_sp]
GO
USE [master]
GO
ALTER DATABASE [Yame Shop] SET  READ_WRITE 
GO
