import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;

import Database.DatabaseConnection;
public class Pokemonimport{
    private static String serverName = "titan.csse.rose-hulman.edu";
    private static String databaseName = "aaPKTest";
    private static String username = "weiy5";
    private static String password = "Womeiyoumima123";
    public static void main(String[] args) throws SQLException{
        DatabaseConnection db = new DatabaseConnection(serverName, databaseName);
        db.connect(username, password);
        //add foreign key constraints
        CallableStatement stmtpokedex = db.getConnection().prepareCall(
                "Create table Pokedex"
                        + "(ID smallint NOT NULL PRIMARY KEY, "
                        + "SpeciesID smallint NOT NULL, Name nvarchar(50) NOT NULL, "
                        + "Type1 nchar(10) NOT NULL, "
                        + "Type2 nchar(10), "
                        + "Total smallint NOT NULL, "
                        + "HP tinyint NOT NULL, "
                        + "ATK tinyint NOT NULL, "
                        + "DEF tinyint NOT NULL, "
                        + "SPA tinyint NOT NULL, "
                        + "SPD tinyint NOT NULL, "
                        + "SPE tinyint NOT NULL)");
        CallableStatement stmttrainer = db.getConnection().prepareCall(
                "Create table Trainer"
                        + "(ID int  NOT NULL PRIMARY KEY, "
                        + "Gender bit, "
                        + "Name varchar(10) NOT NULL, "
                        + "Money int NOT NULL, "
                        + "Badge tinyint NOT NULL)");
        CallableStatement stmtItem1= db.getConnection().prepareCall(
                "Create table Item1 "
                        + "(ID smallint NOT NULL PRIMARY KEY, "
                        + "Name nvarchar(50) NOT NULL, "
                        + "Category nvarchar(50) NOT NULL, "
                        + "Effect nvarchar(200))");
        CallableStatement stmtability1 = db.getConnection().prepareCall(
                "Create table Ability1 "
                        + "(ID smallint NOT NULL PRIMARY KEY, "
                        + "Name nvarchar(50) NOT NULL, "
                        + "Pok_mon tinyint NOT NULL, "
                        + "Description nvarchar(150), "
                        + "Gen tinyint NOT NULL)");
        CallableStatement stmtNature = db.getConnection().prepareCall(
                "Create table Nature"
                        + "(ID tinyint NOT NULL PRIMARY KEY, "
                        + "Name nvarchar(10) NOT NULL, "
                        + "ATK float NOT NULL, "
                        + "DEF float NOT NULL, "
                        + "SPA float NOT NULL, "
                        + "SPD float NOT NULL, "
                        + "SPE float NOT NULL)");
        CallableStatement stmtMove1 = db.getConnection().prepareCall(
                "Create table Move1"
                        + "(ID smallint  NOT NULL PRIMARY KEY, "
                        + "Name nvarchar(50) NOT NULL, "
                        + "Type nvarchar(50) NOT NULL, "
                        + "Cat nvarchar(50), "
                        + "Power tinyint, "
                        + "Acc tinyint, "
                        + "PP tinyint, "
                        + "TM nvarchar(50), "
                        + "Effect nvarchar(200) NOT NULL, "
                        + "Prob nvarchar(50) NOT NULL)");
        CallableStatement stmtpokemon1 = db.getConnection().prepareCall(
                "Create table Pokemon1 "
                        + "(PID int  NOT NULL PRIMARY KEY, "
                        + "Name nvarchar(50) NOT NULL, "
                        + "Gender bit, "
                        + "[Level] int NOT NULL, "
                        + "Friendship tinyint NOT NULL, "
                        + "HP smallint NOT NULL, "
                        + "ATK smallint NOT NULL, "
                        + "DEF smallint NOT NULL, "
                        + "SPA smallint NOT NULL, "
                        + "SPD smallint NOT NULL, "
                        + "SPE smallint NOT NULL, "
                        + "Nature tinyint NOT NULL FOREIGN KEY REFERENCES Nature(ID), "
                        + "SpeciesID smallint NOT NULL FOREIGN KEY REFERENCES Pokedex(ID), "
                        + "AbilityID smallint NOT NULL FOREIGN KEY REFERENCES Ability1(ID), "
                        + "ItemID smallint FOREIGN KEY REFERENCES Item1(ID), "
                        + "TrainerID int NOT NULL FOREIGN KEY REFERENCES Trainer(ID), "
                        + "Move1ID smallint NOT NULL FOREIGN KEY REFERENCES Move1(ID), "
                        + "Move2ID smallint FOREIGN KEY REFERENCES Move1(ID), "
                        + "Move3ID smallint FOREIGN KEY REFERENCES Move1(ID), "
                        + "Move4ID smallint FOREIGN KEY REFERENCES Move1(ID), "
                        + "Favorite bit)");
        stmtpokedex.execute();
        stmttrainer.execute();
        stmtItem1.execute();
        stmtability1.execute();
        stmtNature.execute();
        stmtMove1.execute();
        stmtpokemon1.execute();
        CallableStatement stmtpokedexsp = db.getConnection().prepareCall("Create Procedure createPokedex\r\n"
                + "@ID smallint,\r\n"
                + "@SpeicesID smallint,\r\n"
                + "@Name nvarchar(50),\r\n"
                + "@Type1 nchar(10),\r\n"
                + "@Type2 nchar(10),\r\n"
                + "@Total smallint,\r\n"
                + "@HP tinyint,\r\n"
                + "@ATK tinyint,\r\n"
                + "@DEF tinyint,\r\n"
                + "@SPA tinyint,\r\n"
                + "@SPD tinyint,\r\n"
                + "@SPE tinyint\r\n"
                + "AS\r\n"
                + "Insert into Pokedex VALUES\r\n"
                + "(@ID, @SpeicesID, @Name, @Type1, @Type2, @Total, @HP, @ATK, @DEF, @SPA, @SPD, @SPE)");
        stmtpokedexsp.execute();
        CallableStatement stmtability1sp = db.getConnection().prepareCall("Create Procedure CreateAbility\r\n"
                + "@ID smallint,\r\n"
                + "@Name nvarchar(50),\r\n"
                + "@Pok_mon tinyint,\r\n"
                + "@Description nvarchar(150),\r\n"
                + "@Gen tinyint\r\n"
                + "AS\r\n"
                + "Insert INTO Ability1 Values (@ID, @Name, @Pok_mon, @Description, @Gen)");
        stmtability1sp.execute();
        CallableStatement stmtmove1sp = db.getConnection().prepareCall("Create Procedure CreateMove\r\n"
                + "@ID smallint,\r\n"
                + "@Name nvarchar(50),\r\n"
                + "@Type nvarchar(50),\r\n"
                + "@Cat nvarchar(50),\r\n"
                + "@Power tinyint,\r\n"
                + "@Acc tinyint,\r\n"
                + "@PP tinyint,\r\n"
                + "@TM nvarchar(50),\r\n"
                + "@Effect nvarchar(200),\r\n"
                + "@Prob nvarchar(50)\r\n"
                + "AS\r\n"
                + "Insert INTO Move1 VALUES(@ID, @Name, @Type, @Cat, @Power, @Acc, @PP, @TM, @Effect, @Prob)");
        stmtmove1sp.execute();
        CallableStatement stmtitem1sp = db.getConnection().prepareCall("Create Procedure Createitem\r\n"
                + "@ID smallint,\r\n"
                + "@Name nvarchar(50),\r\n"
                + "@Category nvarchar(50),\r\n"
                + "@Effect nvarchar(200)\r\n"
                + "AS\r\n"
                + "Insert Into Item1 VALUES\r\n"
                + "(@ID, @Name, @Category, @Effect)");
        stmtitem1sp.execute();

        CallableStatement stmtaddnewtrainer = db.getConnection().prepareCall(
                "CREATE Procedure add_new_trainer\r\n"
                        + "	(@Name varchar(10),\r\n"
                        + "	@Gender bit)\r\n"
                        + "AS\r\n"
                        + "	IF(@Name is null) \r\n"
                        + "	BEGIN\r\n"
                        + "		RAISERROR('Pls Enter valid Name',14,1);\r\n"
                        + "		Return 1;\r\n"
                        + "	END\r\n"
                        + "\r\n"
                        + "	IF(@Gender is null)\r\n"
                        + "	BEGIN\r\n"
                        + "		RAISERROR('Pls Enter the Gender',14,2);\r\n"
                        + "		Return 2;\r\n"
                        + "	END\r\n"
                        + "\r\n"
                        + "	IF(@Name is not null AND @Gender is not null)\r\n"
                        + "	BEGIN\r\n"
                        + "		INSERT INTO Trainer \r\n"
                        + "		( ID, Gender, [Name], [Money], Badge)\r\n"
                        + "		VALUES ((Select MAX(ID) FROM Trainer)+1, @Gender, @Name, 0, 0)\r\n"
                        + "	END");
        stmtaddnewtrainer.execute();
        CallableStatement stmtaddSomeRandomPokemon = db.getConnection().prepareCall(
                "Create PROCEDURE addSomeRandomPokemon\r\n"
                        + "(@number int)\r\n"
                        + "AS\r\n"
                        + "if(@number is NULL)\r\n"
                        + "	BEGIN\r\n"
                        + "		RAISERROR('Please enter a number', 14,1);\r\n"
                        + "		return 1\r\n"
                        + "	END\r\n"
                        + "DECLARE @indexe int = 0;\r\n"
                        + "while(@indexe<@number)\r\n"
                        + "	BEGIN\r\n"
                        + "		DECLARE @rTrainer int = (SELECT TOP 1 ID From Trainer ORDER BY NEWID())\r\n"
                        + "		DECLARE @rGender bit = null;\r\n"
                        + "		if(RAND()>0.6)\r\n"
                        + "			BEGIN\r\n"
                        + "				SET @rGender = 0\r\n"
                        + "			END\r\n"
                        + "		else if(RAND()<0.4)\r\n"
                        + "			BEGIN\r\n"
                        + "				SET @rGender = 1\r\n"
                        + "			END\r\n"
                        + "		DECLARE @rSpeciesID smallint = (SELECT TOP 1 ID From Pokedex ORDER BY NEWID())\r\n"
                        + "		DECLARE @rNAME nvarchar(100) = (SELECT Name From Pokedex Where ID = @rSpeciesID)\r\n"
                        + "		--DECLARE @rNAME nvarchar(50) = 'aa';\r\n"
                        + "		if (charindex('(', @rNAME) <> 0)\r\n"
                        + "		Begin\r\n"
                        + "			Set @rNAME = SUBSTRING(@rNAME, 0, charindex(' ', @rNAME));\r\n"
                        + "		End\r\n"
                        + "\r\n"
                        + "		if (charindex(' ', @rNAME) <> 0)\r\n"
                        + "		Begin\r\n"
                        + "			Set @rNAME = SUBSTRING(@rNAME, charindex(' ', @rNAME) + 1, len(@rName));\r\n"
                        + "		End\r\n"
                        + "		\r\n"
                        + "		if (charindex(' ', @rNAME) <> 0)\r\n"
                        + "		Begin\r\n"
                        + "			Set @rNAME = SUBSTRING(@rNAME, 0, charindex(' ', @rNAME));\r\n"
                        + "		End\r\n"
                        + "		DECLARE @rMove1ID smallint = (SELECT TOP 1 ID From Move1 ORDER BY NEWID())\r\n"
                        + "		DECLARE @rItemID smallint = (SELECT TOP 1 ID From Item1 Order BY NEWID())\r\n"
                        + "		DECLARE @rNature tinyint = (SELECT TOP 1 ID From Nature ORDER BY NEWID())\r\n"
                        + "		DECLARE @rLEVEL tinyint = RAND()*100;\r\n"
                        + "		DECLARE @rfriendship tinyint = RAND()*255;\r\n"
                        + "		PRINT @rNAME;\r\n"
                        + "		DECLARE @RHP smallint = (((SELECT Distinct HP From Pokedex Where ID = @rSpeciesID) * 2*@rLEVEL/100)+10);\r\n"
                        + "		DECLARE @RATK smallint = (((SELECT Distinct ATK From Pokedex Where ID = @rSpeciesID) * 2*@rLEVEL/100)+5)*(Select Distinct ATK From Nature Where ID = @rNature);\r\n"
                        + "		DECLARE @RDEF smallint = (((SELECT Distinct DEF From Pokedex Where ID = @rSpeciesID) * 2*@rLEVEL/100)+5)*(Select Distinct DEF From Nature Where ID = @rNature);\r\n"
                        + "		DECLARE @RSPA smallint = (((SELECT Distinct SPA From Pokedex Where ID = @rSpeciesID) * 2*@rLEVEL/100)+5)*(Select Distinct SPA From Nature Where ID = @rNature);\r\n"
                        + "		DECLARE @RSPD smallint = (((SELECT Distinct SPD From Pokedex Where ID = @rSpeciesID) * 2*@rLEVEL/100)+5)*(Select Distinct SPD From Nature Where ID = @rNature);\r\n"
                        + "		DECLARE @RSPE smallint = (((SELECT Distinct SPE From Pokedex Where ID = @rSpeciesID) * 2*@rLEVEL/100)+5)*(Select Distinct SPE From Nature Where ID = @rNature);\r\n"
                        + "		DECLARE @RAbility smallint = (SELECT TOP 1 ID From Ability1 ORDER BY NEWID())\r\n"
                        + "		INSERT INTO Pokemon1\r\n"
                        + "		([TrainerID], [Name], [Gender], [Level],[Friendship],[SpeciesID],[AbilityID],[ItemID],[Move1ID], [PID], [Nature], [HP], [ATK], [DEF], [SPA], [SPD], [SPE])\r\n"
                        + "		VALUES (@rTrainer,\r\n"
                        + "			@rNAME,\r\n"
                        + "			@rGender,\r\n"
                        + "			@rLEVEL,\r\n"
                        + "			@rfriendship,\r\n"
                        + "			@rSpeciesID,\r\n"
                        + "			@RAbility,\r\n"
                        + "			@rItemID,\r\n"
                        + "			@rMove1ID,\r\n"
                        + "			(SELECT MAX(PID) FROM Pokemon1) + 1,\r\n"
                        + "			@rNature,\r\n"
                        + "			@RHP,\r\n"
                        + "			@RATK,@RDEF, @RSPA, @RSPD, @RSPE)\r\n"
                        + "		SET @indexe = @indexe + 1;\r\n"
                        + "	END");
        stmtaddSomeRandomPokemon.execute();
        CallableStatement stmtdeletepokemon = db.getConnection().prepareCall(
                "CREATE Procedure delete_pokemon\r\n"
                        + "(@PID int)\r\n"
                        + "As\r\n"
                        + "if (@PID is NULL)\r\n"
                        + "Begin\r\n"
                        + "Raiserror('PID is NULL',14,1)\r\n"
                        + "Return 1\r\n"
                        + "End\r\n"
                        + "if (@PID not in (Select PID From Pokemon1 Where PID = @PID))\r\n"
                        + "Begin\r\n"
                        + "Raiserror('PID not exists',14,2)\r\n"
                        + "Return 2\r\n"
                        + "End\r\n"
                        + "Delete From Pokemon1\r\n"
                        + "Where PID = @PID\r\n"
                        + "Return 0");
        stmtdeletepokemon.execute();
        CallableStatement stmtdeletetrainer = db.getConnection().prepareCall(
                "CREATE Procedure delete_trainer\r\n"
                        + "	(@ID int)\r\n"
                        + "AS\r\n"
                        + "	IF(@ID is null OR @ID not in(SELECT ID FROM Trainer)) \r\n"
                        + "	BEGIN\r\n"
                        + "		RAISERROR('Pls enter valid ID!',14,1);\r\n"
                        + "		RETURN 1;\r\n"
                        + "	END\r\n"
                        + "	ELSE\r\n"
                        + "	BEGIN\r\n"
                        + "		DELETE Trainer\r\n"
                        + "		WHERE (ID = @ID)\r\n"
                        + "	END");
        stmtdeletetrainer.execute();
//        CallableStatement stmtfindpokemon = db.getConnection().prepareCall(
//        		"CREATE Procedure find_pokemon\r\n"
//        		+ "	(@PID [int] = null,\r\n"
//        		+ "	@Specie varchar(20) = null,\r\n"
//        		+ "	@Gender[bit] = NULL,\r\n"
//        		+ "	@Level [smallint] = null,\r\n"
//        		+ "	@Trainer [smallint] = null)\r\n"
//        		+ "AS\r\n"
//        		+ "	IF(@PID is null AND @Specie is null AND @Gender is NULL AND @Level is null AND @Trainer = null)\r\n"
//        		+ "		BEGIN\r\n"
//        		+ "			SELECT  t.[Name] ,t.Gender AS TrainerGender, t.[Money] AS TrainerMoney, t.Badge AS TrainerBadege, p1.Name AS PokemonName, p1.Attack, p1.Defense, p1.HP, p1.Speed, p1.Total, p1.Type1, p1.Type2\r\n"
//        		+ "			FROM Pokemon1 p\r\n"
//        		+ "			JOIN Trainer t ON t.ID = p.TrainerID\r\n"
//        		+ "			JOIN Pokedex p1 ON p.SpeciesID = p1.SpeciesID\r\n"
//        		+ "		END\r\n"
//        		+ "\r\n"
//        		+ "	IF(@PID is not null)\r\n"
//        		+ "		BEGIN\r\n"
//        		+ "			SELECT  t.[Name] ,t.Gender AS TrainerGender, t.[Money] AS TrainerMoney, t.Badge AS TrainerBadege, p1.Name AS PokemonName, p1.Attack, p1.Defense, p1.HP, p1.Speed, p1.Total, p1.Type1, p1.Type2\r\n"
//        		+ "			FROM Pokemon1 p\r\n"
//        		+ "			JOIN Trainer t ON t.ID = p.TrainerID\r\n"
//        		+ "			JOIN Pokedex p1 ON p.SpeciesID = p1.SpeciesID\r\n"
//        		+ "			WHERE @PID = p.PID\r\n"
//        		+ "		END");
//        stmtfindpokemon.execute();
        CallableStatement stmtgetnewpokemon = db.getConnection().prepareCall(
                "CREATE Procedure get_new_pokemon\r\n"
                        + "	(@TrainerID [int],\r\n"
                        + "	@Name varchar(20),\r\n"
                        + "	@Gender[bit] = NULL,\r\n"
                        + "	@SpeciesID [smallint],\r\n"
                        + "	@AbilityID [smallint])\r\n"
                        + "AS\r\n"
                        + "	IF(@TrainerID is null or @SpeciesID is null or @AbilityID is null)\r\n"
                        + "		BEGIN\r\n"
                        + "			RAISERROR('Please match the requirement to get a new pokemon',14,1);\r\n"
                        + "			return 1;\r\n"
                        + "		END\r\n"
                        + "	IF(@Name is null)\r\n"
                        + "		BEGIN\r\n"
                        + "			SET @Name = (Select [Name] from Pokedex Where ID = @SpeciesID)\r\n"
                        + "		END\r\n"
                        + "	DECLARE @RFriendship tinyint = Rand()*255;\r\n"
                        + "	DECLARE @rNature tinyint = (SELECT TOP 1 ID From Nature ORDER BY NEWID())\r\n"
                        + "	DECLARE @rMove1ID smallint = (SELECT TOP 1 ID From Move1 ORDER BY NEWID())\r\n"
                        + "	DECLARE @rItemID smallint = (SELECT TOP 1 ID From Item1 Order BY NEWID())\r\n"
                        + "		DECLARE @RHP smallint = (((SELECT Distinct HP From Pokedex Where ID = @SpeciesID) * 2*1/100)+10);\r\n"
                        + "		DECLARE @RATK smallint = (((SELECT Distinct ATK From Pokedex Where ID = @SpeciesID) * 2*1/100)+5)*(Select Distinct ATK From Nature Where ID = @rNature);\r\n"
                        + "		DECLARE @RDEF smallint = (((SELECT Distinct DEF From Pokedex Where ID = @SpeciesID) * 2*1/100)+5)*(Select Distinct DEF From Nature Where ID = @rNature);\r\n"
                        + "		DECLARE @RSPA smallint = (((SELECT Distinct SPA From Pokedex Where ID = @SpeciesID) * 2*1/100)+5)*(Select Distinct SPA From Nature Where ID = @rNature);\r\n"
                        + "		DECLARE @RSPD smallint = (((SELECT Distinct SPD From Pokedex Where ID = @SpeciesID) * 2*1/100)+5)*(Select Distinct SPD From Nature Where ID = @rNature);\r\n"
                        + "		DECLARE @RSPE smallint = (((SELECT Distinct SPE From Pokedex Where ID = @SpeciesID) * 2*1/100)+5)*(Select Distinct SPE From Nature Where ID = @rNature);\r\n"
                        + "		DECLARE @RAbility smallint = (SELECT TOP 1 ID From Ability1 ORDER BY NEWID())\r\n"
                        + "		INSERT INTO Pokemon1\r\n"
                        + "		([TrainerID], [Name], [Gender], [Level],[Friendship],[SpeciesID],[AbilityID],[ItemID],[Move1ID], [PID], [Nature], [HP], [ATK], [DEF], [SPA], [SPD], [SPE])\r\n"
                        + "		VALUES (@TrainerID,\r\n"
                        + "			@NAME,\r\n"
                        + "			@Gender,\r\n"
                        + "			1,\r\n"
                        + "			@rfriendship,\r\n"
                        + "			@SpeciesID,\r\n"
                        + "			@RAbility,\r\n"
                        + "			@rItemID,\r\n"
                        + "			@rMove1ID,\r\n"
                        + "			(SELECT MAX(PID) FROM Pokemon1) + 1,\r\n"
                        + "			@rNature,\r\n"
                        + "			@RHP,\r\n"
                        + "			@RATK,@RDEF, @RSPA, @RSPD, @RSPE)");
        stmtgetnewpokemon.execute();
        CallableStatement stmtupdatepokemon = db.getConnection().prepareCall(
                "CREATE Procedure update_pokemon\r\n"
                        + "	(@PID [int] = null,\r\n"
                        + "	@Name varchar(20) = null,\r\n"
                        + "	@Level tinyint = null,\r\n"
                        + "	@Fav bit = 0,\r\n"
                        + "	@Friendship tinyint = null)\r\n"
                        + "AS\r\n"
                        + "	IF(@PID is null OR @PID not in (SELECT PID FROM Pokemon1))\r\n"
                        + "	BEGIN\r\n"
                        + "		RAISERROR('Invalid PID!',14,1);\r\n"
                        + "		Return 1;\r\n"
                        + "	END\r\n"
                        + "\r\n"
                        + "	IF(@Name is not null OR @Name != '')\r\n"
                        + "	BEGIN\r\n"
                        + "		UPDATE Pokemon1\r\n"
                        + "		SET [Name] = @Name\r\n"
                        + "		WHERE PID = @PID\r\n"
                        + "	END\r\n"
                        + "\r\n"
                        + "	IF(@Level is not null)\r\n"
                        + "	BEGIN\r\n"
                        + "		UPDATE Pokemon1\r\n"
                        + "		SET [Level] = @Level\r\n"
                        + "		WHERE PID = @PID\r\n"
                        + "	END\r\n"
                        + "\r\n"
                        + "	IF(@Friendship is not null)\r\n"
                        + "	BEGIN\r\n"
                        + "		UPDATE Pokemon1\r\n"
                        + "		SET Friendship = @Friendship\r\n"
                        + "		WHERE PID = @PID\r\n"
                        + "	END\r\n"
                        + "\r\n"
                        + "\r\n"
                        + "	IF(@Fav = 1)\r\n"
                        + "	BEGIN\r\n"
                        + "		UPDATE Pokemon1\r\n"
                        + "		SET [Favorite] = 1\r\n"
                        + "		WHERE PID = @PID\r\n"
                        + "	END\r\n"
                        + "	ELSE\r\n"
                        + "	BEGIN\r\n"
                        + "		UPDATE Pokemon1\r\n"
                        + "		SET [Favorite] = 0\r\n"
                        + "		WHERE PID = @PID\r\n"
                        + "	END");
        stmtupdatepokemon.execute();
        CallableStatement stmtfunc = db.getConnection().prepareCall(
                "CREATE function Func(@PID int = NULL, @Level int = NULL, @SName nvarchar(50)= NULL, \r\n"
                        + "@AName nvarchar(50) = NULL, @TName nvarchar(50) = NULL)\r\n"
                        + "returns @Search_Table table(PID int, PName Varchar(20), PGender Bit, Level int, Friendship int, \r\n"
                        + "SName nvarchar(50), AName nvarchar(50), TName Varchar(10))\r\n"
                        + "as\r\n"
                        + "begin\r\n"
                        + "  insert into @Search_Table\r\n"
                        + "  Select PID, Pokemon1.Name as PName,Pokemon1.Gender as PGender, Level, Friendship,\r\n"
                        + "Pokedex.Name as SName, Ability1.Name as AName, Trainer.Name as TName\r\n"
                        + "From Pokemon1 \r\n"
                        + "Join Pokedex On Pokemon1.SpeciesID = Pokedex.ID\r\n"
                        + "Join Ability1 On Pokemon1.AbilityID = Ability1.ID\r\n"
                        + "Join Trainer On Pokemon1.TrainerID = Trainer.ID\r\n"
                        + "Declare @New_Table table(PID int, PName Varchar(20), PGender Bit, Level int, Friendship int, \r\n"
                        + "SName nvarchar(50), AName nvarchar(50), TName Varchar(10))\r\n"
                        + "if (@PID is not NULL)\r\n"
                        + "Begin\r\n"
                        + "Delete From @Search_Table Where PID <> @PID;\r\n"
                        + "End\r\n"
                        + "if (@Level is not NULL)\r\n"
                        + "Begin\r\n"
                        + "Delete From @Search_Table Where Level <> @Level;\r\n"
                        + "End\r\n"
                        + "if (@SName is not NULL)\r\n"
                        + "Begin\r\n"
                        + "Delete From @Search_Table Where SName <> @SName;\r\n"
                        + "END\r\n"
                        + "if (@AName is not NULL)\r\n"
                        + "Begin\r\n"
                        + "Delete From @Search_Table Where AName <> @AName;\r\n"
                        + "END\r\n"
                        + "if (@TName is not NULL)\r\n"
                        + "Begin\r\n"
                        + "Delete From @Search_Table Where TName <> @TName;\r\n"
                        + "END\r\n"
                        + "\r\n"
                        + "return\r\n"
                        + "end");
        stmtfunc.execute();
        CallableStatement stmtsearchpokemon = db.getConnection().prepareCall(
                "CREATE Function search_pokemon\r\n"
                        + "()\r\n"
                        + "Returns TABLE\r\n"
                        + "As\r\n"
                        + "Return\r\n"
                        + "(Select PID, Pokemon1.Name as PName,Pokemon1.Gender as PGender, Level, Nature.Name as Nature,\r\n"
                        + "Friendship, Pokedex.Name as SName, Ability1.Name as AName, Item1.Name as IName, Trainer.Name as TName,\r\n"
                        + "Pokemon1.HP as HP,\r\n"
                        + "Pokemon1.ATK as ATK, \r\n"
                        + "Pokemon1.DEF as DEF, \r\n"
                        + "Pokemon1.SPA as SPA, \r\n"
                        + "Pokemon1.SPD as SPD, \r\n"
                        + "Pokemon1.SPE as SPE, \r\n"
                        + "Pokemon1.Favorite\r\n"
                        + "From Pokemon1 \r\n"
                        + "Join Pokedex On Pokemon1.SpeciesID = Pokedex.ID\r\n"
                        + "Join Nature On Pokemon1.Nature = Nature.ID\r\n"
                        + "Join Ability1 On Pokemon1.AbilityID = Ability1.ID\r\n"
                        + "Join Item1 On Pokemon1.ItemID = Item1.ID\r\n"
                        + "Join Trainer On Pokemon1.TrainerID = Trainer.ID)");
        stmtsearchpokemon.execute();
        CallableStatement stmtTrainerWithPokemonMoreThan = db.getConnection().prepareCall(
                "CREATE FUNCTION TrainerWithPokemonMoreThan\r\n"
                        + "(\r\n"
                        + "	@maxCount int\r\n"
                        + ")\r\n"
                        + "RETURNS table\r\n"
                        + "AS \r\n"
                        + "RETURN \r\n"
                        + "(\r\n"
                        + "	SELECT t.Name, COUNT(p.PID) AS NumOfPokemon\r\n"
                        + "	FROM Trainer t\r\n"
                        + "	JOIN Pokemon1 p ON t.ID = p.TrainerID\r\n"
                        + "	GROUP BY p.TrainerID, t.Name\r\n"
                        + "	HAVING COUNT(p.PID) >= @maxCount\r\n"
                        + ")");
        stmtTrainerWithPokemonMoreThan.execute();

        String line = "";
        String splitBy = ",";
        CallableStatement stmt = null;
        int index = 1;
        int index1 = 1;
        int index2 = 1;
        int index3 = 1;
        try{
            BufferedReader pokedex = new BufferedReader(new FileReader(".\\src\\pokedex.csv"));
            BufferedReader item = new BufferedReader(new FileReader(".\\src\\item.csv"));
            BufferedReader move = new BufferedReader(new FileReader(".\\src\\move.csv"));
            BufferedReader ability = new BufferedReader(new FileReader(".\\src\\ability.csv"));
            while ((line = pokedex.readLine()) != null){
                String[] pokemon = line.split(splitBy);    // use comma as separator
                try {
                    stmt = db.getConnection().prepareCall("{call createPokedex(@ID = ?, @SpeicesID=?, @Name=?, @Type1=?, @Type2=?, @Total=?, @HP=?, @ATK=?, @DEF=?, @SPA=?, @SPD=?, @SPE=?)}");
                    if(pokemon.length==10){
                        stmt.setInt(1, index);
                        stmt.setInt(2, Integer.parseInt(pokemon[0]));
                        stmt.setString(3, pokemon[1]);
                        stmt.setString(4, pokemon[2]);
                        stmt.setString(5, null);
                        stmt.setInt(6, Integer.parseInt(pokemon[3]));
                        stmt.setInt(7, Integer.parseInt(pokemon[4]));
                        stmt.setInt(8, Integer.parseInt(pokemon[5]));
                        stmt.setInt(9, Integer.parseInt(pokemon[6]));
                        stmt.setInt(10, Integer.parseInt(pokemon[7]));
                        stmt.setInt(11, Integer.parseInt(pokemon[8]));
                        stmt.setInt(12, Integer.parseInt(pokemon[9]));
                    }else {
                        stmt.setInt(1, index);
                        stmt.setInt(2, Integer.parseInt(pokemon[0]));
                        stmt.setString(3, pokemon[1]);
                        stmt.setString(4, pokemon[2]);
                        stmt.setString(5, pokemon[3]);
                        stmt.setInt(6, Integer.parseInt(pokemon[4]));
                        stmt.setInt(7, Integer.parseInt(pokemon[5]));
                        stmt.setInt(8, Integer.parseInt(pokemon[6]));
                        stmt.setInt(9, Integer.parseInt(pokemon[7]));
                        stmt.setInt(10, Integer.parseInt(pokemon[8]));
                        stmt.setInt(11, Integer.parseInt(pokemon[9]));
                        stmt.setInt(12, Integer.parseInt(pokemon[10]));
                    }
                    stmt.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                index++;
            }
            while ((line = ability.readLine()) != null){
                String[] pokemon = line.split(splitBy);    // use comma as separator
                try {
                    stmt = db.getConnection().prepareCall("{call CreateAbility (@ID = ?, @Name = ?, @Pok_mon = ?, @Description = ?, @Gen = ?)}");
                    stmt.setInt(1, index1);
                    stmt.setString(2, pokemon[0]);
                    stmt.setInt(3, Integer.parseInt(pokemon[1]));
                    stmt.setString(4, pokemon[2]);
                    stmt.setInt(5, Integer.parseInt(pokemon[3]));
                    stmt.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                index1++;
            }
            while ((line = move.readLine()) != null){
                String[] pokemon = line.split(splitBy);    // use comma as separator
                try {
                    stmt = db.getConnection().prepareCall("{call CreateMove (@ID = ?, @Name = ?, @Type = ?, @Cat = ?, @Power = ?, @Acc = ?, @PP = ?, @TM = ?, @Effect = ?, @Prob = ?)}");
                    stmt.setInt(1, index2);
                    stmt.setString(2, pokemon[0]);
                    stmt.setString(3, pokemon[1]);
                    stmt.setString(4, pokemon[2]);
                    if(pokemon[3].equals("NULL")) {
                        stmt.setString(5, null);
                    }else {
                        stmt.setInt(5, Integer.parseInt(pokemon[3]));
                    }
                    stmt.setInt(6, Integer.parseInt(pokemon[4]));
                    if(pokemon[5].equals("NULL")) {
                        stmt.setString(7, null);
                    }else {
                        stmt.setInt(7, Integer.parseInt(pokemon[5]));
                    }
                    stmt.setString(8, pokemon[6]);
                    stmt.setString(9, pokemon[7]);
                    stmt.setString(10, pokemon[8]);
                    stmt.execute();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                index2++;
            }
            while ((line = item.readLine()) != null){
                String[] pokemon = line.split(splitBy);    // use comma as separator
                try {
                    stmt = db.getConnection().prepareCall("{call Createitem (@ID = ?, @Name = ?, @Category = ?, @Effect  = ?)}");
                    if(pokemon.length==2) {
                        stmt.setInt(1, index3);
                        stmt.setString(2, pokemon[0]);
                        stmt.setString(3, pokemon[1]);
                        stmt.setString(4, null);
                    }else {
                        stmt.setInt(1, index3);
                        stmt.setString(2, pokemon[0]);
                        stmt.setString(3, pokemon[1]);
                        stmt.setString(4, pokemon[2]);
                    }
                    stmt.execute();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                index3++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}  