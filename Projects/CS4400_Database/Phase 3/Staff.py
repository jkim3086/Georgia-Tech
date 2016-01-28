import pymysql
from tkinter import *
from time import gmtime, strftime
import datetime

class Staff:
    def __init__(r):
        
        r.root = Tk()
        r.root.configure(bg="gold")    #### I tried to seperate windows with 2 sperate Frames, but if label is in a Frame, Frame's bg color doesn't function. I searched but they say OSX doesn't support it. do you have any solution?

        label = Label(r.root, text="Staff Login", bg = "gold")
        label.grid(row = 0, columnspan = 4, padx = 5,pady=5,sticky=W)

        label2 = Label(r.root, text="Username :",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.loginuserEntry = Entry(r.root, width = 30)
        r.loginuserEntry.grid(row = 1,column=1, columnspan = 3, sticky=W,padx=5,pady=5)
        label3 = Label(r.root, text="password :",bg="gold").grid(row = 2, column = 0,padx=5, pady = 5,sticky = E)
        r.loginpasswordEntry = Entry(r.root, width = 30)
        r.loginpasswordEntry.grid(row=2, column=1, columnspan = 3, padx=5,pady=5,sticky=W)

        button2 = Button(r.root,text="LogIn", command = r.selectScreen).grid(row=3,column=4,sticky = W)
        r.root.mainloop()

    def connect(r):
        try:
            r.db = pymysql.connect(host = 'academic-mysql.cc.gatech.edu', passwd = 'LeQs1rXt' , user = 'cs4400_Group_75', db='cs4400_Group_75')
            r.cursor = r.db.cursor()
        except:
            return None

    def loginCheck(r):
        r.loginuser = str(r.loginuserEntry.get())
        password = r.loginpasswordEntry.get()
        r.connect()
        if(r.cursor != None):
            sql = 'SELECT COUNT(USERNAME) FROM USER WHERE USERNAME = %s AND PASSWORD = %s'
            try:
                r.cursor.execute(sql, (r.loginuser, password))
                if(r.cursor.fetchone()[0] == 1):
                    return True
                else:
                    messagebox.showerror('error!', 'Wrong username or password')
            except:
                messagebox.showerror('error!', 'Some Error occured sorry')
        else:
            print('not connected')
        return False

    def selectScreen(r):
        r.selectscreen = Toplevel(r.root)
        r.root.withdraw()
        label = Label(r.selectscreen, text = "SELECT SCREEN").grid(row = 0, column = 0, columnspan = 4)
        checkout = Button(r.selectscreen, text="Checkout Screen", command = r.checkOutScreen).grid(row=1,column=0,sticky = W)
        returnbook = Button(r.selectscreen, text="Return book Screen", command = r.returnBookScreen).grid(row=2,column = 0,sticky=W)
        penalty = Button(r.selectscreen, text="Damaged/Lost book Screen", command = r.penaltyScreen).grid(row=3,column = 0, sticky=W)
        damagedbookreport = Button(r.selectscreen, text="Damaged book report", command = r.damagedBookScreen).grid(row=4, column = 0, sticky=W)
        frequentreport = Button(r.selectscreen, text="frequent book report", command = r.frequentBookScreen).grid(row=5, column = 0, sticky=W)
        popularreport = Button(r.selectscreen, text="popular book report", command = r.popularReportScreen).grid(row=6, column = 0, sticky=W)
        popularsubject = Button(r.selectscreen, text="popular subject report", command = r.popularSubjectScreen).grid(row=7, column = 0, sticky=W)
       
    def toSelectScreen(r, s):
        if s == 'checkout':
            r.selectscreen.deiconify()
            r.checkoutscreen.withdraw()
        if s == 'returnbook':
            r.selectscreen.deiconify()
            r.returnbookscreen.withdraw()
        if s == 'penalty':
            r.selectscreen.deiconify()
            r.penaltyscreen.withdraw()
        if s == 'damagedbookreport':
            r.selectscreen.deiconify()
            r.damagedbook.withdraw()
        if s == 'frequentreport':
            r.selectscreen.deiconify()
            r.frequentreport.withdraw()
            
    #######################CHECK OUT SCREEN#########################
    def checkOutScreen(r):
        r.checkoutscreen = Toplevel(r.selectscreen)
        r.selectscreen.withdraw()
        r.count = -1
        
        label2 = Label(r.checkoutscreen, text="issue_id ",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.issueEntry = Entry(r.checkoutscreen, width = 10)
        r.issueEntry.grid(row=1, column=1)
        username = Label(r.checkoutscreen, text="username ",bg="gold").grid(row =1, column=2,padx = 5, pady = 5,sticky = E)
        r.userentry = Entry(r.checkoutscreen, width = 10)
        r.userentry.grid(row=1, column=3)

        isbn = Label(r.checkoutscreen, text="isbn",bg="gold").grid(row =2, column=0,padx = 5, pady = 5,sticky = E)
        r.isbnentry = Entry(r.checkoutscreen, width = 10, state="readonly")
        r.isbnentry.grid(row=2, column=1)
        copy = Label(r.root, text="copy n",bg="gold").grid(row =2, column=2,padx = 5, pady = 5,sticky = E)
        r.copyentry = Entry(r.checkoutscreen, width = 10, state="readonly")
        r.copyentry.grid(row=2, column=3)

        checkout = Label(r.checkoutscreen, text="check out date",bg="gold").grid(row = 3, column=0,padx = 5, pady = 5,sticky = E)
        r.checkentry = Entry(r.checkoutscreen, width = 10, state="readonly")
        r.checkentry.grid(row=3, column=1)
        red = Label(r.root, text="estimated return date",bg="gold").grid(row =3, column=2,padx = 5, pady = 5,sticky = E)
        r.redentry = Entry(r.checkoutscreen, width = 10, state="readonly")
        r.redentry.grid(row=3, column=3)

        
        button = Button(r.checkoutscreen, text="confirm", command = r.checkOut).grid(row=4,column=3)
        toselect = Button(r.checkoutscreen, text="to select screen", command =  lambda: r.toSelectScreen('checkout')).grid(row=4,column=4)

    def checkOut(r):
        ## update red, ischeckedout, on hold
        issue_id = r.issueEntry.get()
        ## get isbn
        sql = "SELECT ISBN, COPY_N FROM ISSUED_BOOK WHERE ISSUED_ID = %s"
        r.cursor.execute(sql, issue_id)
        isbnAndcopy_n = r.cursor.fetchone()
        isbn = isbnAndcopy_n[0]
        copy_n = isbnAndcopy_n[1]
        today = datetime.date.today()
        red = today + datetime.timedelta(14)
        ## update new redate
        sql = "UPDATE ISSUED_BOOK SET RETURN_DATE = CAST(%s AS DATETIME) WHERE ISSUED_ID = %s"
        r.cursor.execute(sql, (red, issue_id))
        ## update is_checked_out, is_on_hold
        sql = "UPDATE BOOK_COPY SET IS_CHECKED_OUT = %s , IS_ON_HOLD = %s WHERE ISBN = %s AND COPY_N = %s"
        r.cursor.execute(sql, ('Y', 'N', isbn, copy_n))
        ## after 3 days -> drop

        sql = "SELECT DATE_OF_ISSUED FROM ISSUED_BOOK WHERE ISSUED_ID = %s"
        r.cursor.execute(sql, issue_id)
        dateofissue = r.cursor.fetchone()[0]
        if today > dateofissue + datetime.timedelta(3):
            sql = "DELETE * FROM ISSUED_BOOK WHERE ISSUED_ID = %s"
            r.cursor.execute(sql, issue_id)
            sql = "UPDATE BOOK_COPY SET IS_ON_HOLD = %s WHERE ISBN = %s AND COPY_N = %s"
            
        ## no copy is available -> request for future                      
        r.db.commit()


    ######################RETURN BOOK SCREEN###################
    def returnBookScreen(r):
        r.count = -1
        r.returnbookscreen = Toplevel(r.selectscreen)
        r.selectscreen.withdraw()
        
        label2 = Label(r.returnbookscreen, text="issue_id ",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.returnbookissueid = Entry(r.returnbookscreen, width = 10)
        r.returnbookissueid.grid(row=1, column=1)
        username = Label(r.returnbookscreen, text="username ").grid(row =1, column=2,padx = 5, pady = 5,sticky = E)
        r.userentry = Entry(r.returnbookscreen, width = 10,state="readonly")
        r.userentry.grid(row=1, column=3)

        isbn = Label(r.returnbookscreen, text="isbn").grid(row =2, column=0,padx = 5, pady = 5,sticky = E)
        r.isbnentry = Entry(r.returnbookscreen, width = 10, state="readonly")
        r.isbnentry.grid(row=2, column=1)
        copy = Label(r.returnbookscreen, text="copy n").grid(row =2, column=2,padx = 5, pady = 5,sticky = E)
        r.copyentry = Entry(r.returnbookscreen, width = 10, state="readonly")
        r.copyentry.grid(row=2, column=3)

        damage = Label(r.returnbookscreen, text="return in damaged condition").grid(row = 3, column=0,padx = 5, pady = 5,sticky = E)
        r.dentry = Entry(r.returnbookscreen, width = 10)
        r.dentry.grid(row=3, column=1)
        
        button = Button(r.returnbookscreen, text="return", command = r.returnBook).grid(row=3,column=2)
        button2 = Button(r.returnbookscreen, text ="selection screen", command = lambda : r.toSelectScreen('returnbook')).grid(row=3,column = 3)

        r.root.mainloop()
        
    def returnBook(r):
        ## check damage of not, update
        issue_id = r.returnbookissueid.get()
        user = r.userentry.get()
        isbn = r.isbnentry.get()
        copy_n = r.copyentry.get()
        damaged = r.dentry.get().strip()
        
        #######FOR TEST PURPOSE I need to enter username, isbn, and copy_n#######
        sql = "SELECT USERNAME, ISBN, COPY_N FROM ISSUED_BOOK WHERE ISSUED_ID = %s"
        r.cursor.execute(sql, issue_id)
        l = r.cursor.fetchone()
        user= l[0]
        isbn = l[1]
        copy_n = l[2]


        #################################################################################
        
        if (damaged == "Y" or damaged == "N"): ##check damaged
            if damaged == "Y":
                r.penaltyScreen()
            else:
                ##check late
                sql = "SELECT RETURN_DATE FROM ISSUED_BOOK WHERE ISBN = %s AND COPY_N = %s"
                r.cursor.execute(sql, (isbn, copy_n))
                red = r.cursor.fetchone()[0]
                if red >= datetime.date.today(): ## returned in correct day
                    sql = "UPDATE BOOK_COPY SET IS_CHECKED_OUT = %s WHERE ISBN = %s AND COPY_N = %s"
                    r.cursor.execute(sql, ("N", isbn, copy_n))
                else: ## when returned after due  --> charge late fee
                    d = datetime.date.today() - red
                    penaltyStr = str(d)
                    penalty = penaltyStr.split()[0]
                    penalty = 0.5 * float(penalty)
                    sql = "UPDATE STUDENT_FACULTY SET PENALTY = %s WHERE USERNAME = (SELECT USERNAME FROM ISSUED_BOOK WHERE ISSUED_ID = %s)"
                    r.cursor.execute(sql, (penalty, issue_id))
                    ##and unmark is_checked_out
                    sql = "UPDATE BOOK_COPY SET IS_CHECKED_OUT = %s WHERE ISBN = %s AND COPY_N = %s"
                    r.cursor.execute(sql, ("N", isbn, copy_n))
        else:
            messagebox.showerror("error!","please enter damaged entry either 'Y' or 'N'!")



    ##############################LOST / DAMAGED BOOK ####################
    def penaltyScreen(r):
        r.penaltyscreen = Toplevel(r.selectscreen)
        r.selectscreen.withdraw()
        isbn = Label(r.penaltyscreen, text="isbn ").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.isbnentry = Entry(r.penaltyscreen, width = 10)
        r.isbnentry.grid(row=1, column=1)
        copy = Label(r.penaltyscreen, text="book copy ").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.copyentry = Entry(r.penaltyscreen, width = 10)
        r.copyentry.grid(row=1, column=2)
        current = Label(r.penaltyscreen, text="current time").grid(row =2, column=0,padx = 5, pady = 5,sticky = E)
        r.timeentry = Entry(r.penaltyscreen, width = 10, state="readonly")
        r.timeentry.grid(row=2, column=1)

        
        lastuser = Label(r.penaltyscreen, text="last user").grid(row =2, column=2,padx = 5, pady = 5,sticky = E)
        r.lastuser = Entry(r.penaltyscreen, width = 10, state="readonly")
        r.lastuser.grid(row=2, column=3)

        amount = Label(r.penaltyscreen, text="amount to be charged",bg="gold").grid(row = 3, column=0,padx = 5, pady = 5,sticky = E)
        r.amount = Entry(r.penaltyscreen, width = 10)
        r.amount.grid(row=3, column=1)
        
        button = Button(r.penaltyscreen, text="look for the last user", command = r.returnBook).grid(row=3,column=2)
        button2 = Button(r.penaltyscreen, text="to select page", command = lambda: r.toSelectScreen('penalty')).grid(row=3, column = 3)
    def chargePenalty(r):
        isbn = r.isbnentry.get()
        copy_n = r.copyentry.get()

        r.timeentry.configure(state=NORMAL)
        r.timeentry.insert(0, str(datetime.date.today()))
        r.timeentry.configure(state=DISABLED)

        sql = "SELECT USERNAME FROM ISSUED_BOOK WHERE RETURN_DATE < CURDATE() AND ISBN = %s AND COPY_N = %s ORDER BY RETURN_DATE DESC LIMIT 1"
        r.cursor.execute(sql, (isbn, copy_n))
        user = r.cursor.fetchone()[0]

        r.lastuser.configure(state=NORMAL)
        r.lastuser.insert(0, user)
        r.lastuser.configure(state=DISABLED)

        amount = r.amount.get()

        sql = "UPDATE STUDENT_FACULTY SET PENALTY = %s WHERE USERNAME = %s"
        r.cursor.execute(sql, (amount, user))

        r.db.commit()
        r.db.close()


    ######################REPORT#####################
    ##########1.DAMAGED BOOK REPORT##################
    def damagedBookScreen(r):
        
        r.damagedbook = Toplevel(r.selectscreen)

        label = Label(r.damagedbook, text="Damaged Book Report", bg = "gold")
        label.grid(row = 0,column = 0, columnspan = 4, padx = 5,pady=5,sticky=W)

        label2 = Label(r.damagedbook, text="Month :",bg="gold").grid(row =1, column=0)
        r.v1 = StringVar()
        r.v1.set('Jan')
        r.months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']
        r.month = OptionMenu(r.damagedbook, r.v1, *r.months)
        r.month.grid(row = 1, column = 1)
        
        label3 = Label(r.damagedbook, text="Subject:",bg="gold").grid(row =1 , column = 2)
        r.s1 = StringVar()
        r.s1.set('')
        sublist1 = r.showSubjects()
        suboption1 = OptionMenu(r.damagedbook, r.s1, *sublist).grid(row=1,column=3)

        label4 = Label(r.damagedbook, text="Subject:",bg="gold").grid(row =2 , column = 2)
        r.s2 = StringVar()
        r.s2.set('')
        sublist2 = r.showSubjects()
        suboption2 = OptionMenu(r.damagedbook, r.s1, *sublist).grid(row=2,column=3)

        label4 = Label(r.damagedbook, text="Subject:",bg="gold").grid(row =3 , column = 2)
        r.s3 = StringVar()
        r.s3.set('')
        sublist3 = r.showSubjects()
        suboption3 = OptionMenu(r.damagedbook, r.s1, *sublist).grid(row=4,column=3)

        button = Button(r.damagedbook, text="showResult", command = r.showResult).grid(row=4, column = 3)
        button2 = Button(r.damagedbook, text="to select screen", command = r.toSelectScreen('damagedbookreport')).grid(row=4, column =4)
        r.showResult()
                         
    def showSubjects(r):
        month = r.v1.get()
        sql = "SELECT SUBJECT_NAME FROM SUBJECT"
        r.cursor.execute(sql)
        ll = r.cursor.fetchall()
        l = []
        for e in ll:
            l.append(e[0])

        return l

    def showResult(r):
        month = r.v1.get()
        cy = datetime.date.today().strftime("%Y")

        dic = {'Jan':1,'Feb':2,'Mar':3,'Apr':4,'May':5,'Jun':6,'Jul':7,'Aug':8,'Sep':9,'Oct':10,'Nov':11,'Dec':12}
       
        sql = '''(SELECT MONTH( ISSUED_BOOK.DATE_OF_ISSUED) AS  'Month', BOOK_BOOK.SUBJECT_NAME, COUNT( * ) AS  '#checkouts', ISSUED_BOOK.ISSUED_ID
        FROM BOOK_BOOK
        NATURAL JOIN ISSUED_BOOK
        NATURAL JOIN BOOK_COPY
        WHERE MONTH(ISSUED_BOOK.RETURN_DATE) = %s
        AND YEAR(ISSUED_BOOK.RETURN_DATE) = %s
        AND BOOK_COPY.IS_DAMAGED =  ‘Y'
        AND SUBJECT_NAME = %s
        GROUP BY SUBJECT_NAME
        LIMIT 0 , 30) UNION (SELECT MONTH( ISSUED_BOOK.DATE_OF_ISSUED) AS  'Month', BOOK_BOOK.SUBJECT_NAME, COUNT( * ) AS  '#checkouts', ISSUED_BOOK.ISSUED_ID
        FROM BOOK_BOOK
        NATURAL JOIN ISSUED_BOOK
        NATURAL JOIN BOOK_COPY
        WHERE MONTH(ISSUED_BOOK.RETURN_DATE) = %s
        AND YEAR(ISSUED_BOOK.RETURN_DATE) = %s
        AND BOOK_COPY.IS_DAMAGED =  ‘Y'
        AND SUBJECT_NAME = %s
        GROUP BY SUBJECT_NAME
        LIMIT 0 , 30) UNION (SELECT MONTH( ISSUED_BOOK.DATE_OF_ISSUED ) AS  'Month', BOOK_BOOK.SUBJECT_NAME, COUNT( * ) AS  '#checkouts', ISSUED_BOOK.ISSUED_ID
        FROM BOOK_BOOK
        NATURAL JOIN ISSUED_BOOK
        NATURAL JOIN BOOK_COPY
        WHERE MONTH(ISSUED_BOOK.RETURN_DATE) = %s
        AND YEAR(ISSUED_BOOK.RETURN_DATE) = %s
        AND BOOK_COPY.IS_DAMAGED =  ‘Y'
        AND SUBJECT_NAME = %s
        GROUP BY SUBJECT_NAME
        LIMIT 0 , 30)'''
        
        ##cy = datetime.date.today().strftime("%Y")
        cy = datetime.date(2010, 1, 1).strftime("%Y")
        r.cursor.execute(sql, (dic[month], cy, r.s1.get(),dic[month], cy, r.s2.get(),dic[month], cy, r.s3.get()))
        tuplelist = r.cursor.fetchall()

        label = Label(r.root, text = 'Month').grid(row=5, column = 0)
        label2 = Label(r.root, text = 'Subject').grid(row=5, column = 1)
        label2 = Label(r.root, text = '#damage books').grid(row=5, column = 2)


        
        i = 6
        labellist = []
        for e in tuplelist:
            labellist.append(Label(r.root, text = month).grid(row=i, column = 0))
            labellist.append(Label(r.root, text = e[1]).grid(row=i, column = 1))
            labellist.append(Label(r.root, text = e[2]).grid(row=i, column = 2))
            i += 1
    ###################FREQUENT BOOK REPORT#######################
    def frequentBookScreen(r):
        
        r.frequentreport = Toplevel(r.selectscreen)
        r.frequentreport.configure(bg="gold")    #### I tried to seperate windows with 2 sperate Frames, but if label is in a Frame, Frame's bg color doesn't function. I searched but they say OSX doesn't support it. do you have any solution?

        label = Label(r.frequentreport, text="popular book report", bg = "gold")
        label.grid(row = 0, columnspan = 4, padx = 5,pady=5,sticky=W)

        label2 = Label(r.frequentreport, text="Month-----",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        label3 = Label(r.frequentreport, text="UserName-----",bg="gold").grid(row = 1, column = 1,padx=5, pady = 5,sticky = E)
        label4 = Label(r.frequentreport, text="# of checkouts-",bg="gold").grid(row = 1, column = 2,padx=5, pady = 5,sticky = E)

        r.showReport()
        
    def showReport(r):
        r.connect()
        r.labellist = []
        sql = '''(SELECT MONTH(ISSUED_BOOK.DATE_OF_ISSUED) AS 'Month', ISSUED_BOOK.USERNAME AS 'User Name', COUNT(*) AS '#checkouts'
        FROM ISSUED_BOOK
        WHERE ISSUED_BOOK.RETURN_DATE <= %s AND ISSUED_BOOK.RETURN_DATE > %s AND (ISSUED_BOOK.COUNT_EXTENSION = 0 OR ISSUED_BOOK.COUNT_EXTENSION IS NULL)
        GROUP BY USERNAME
        HAVING COUNT(*) > 1
        ORDER BY COUNT(*) DESC
        LIMIT 0, 5)
        UNION
        (SELECT MONTH(ISSUED_BOOK.DATE_OF_ISSUED) AS 'Month', ISSUED_BOOK.USERNAME AS 'User Name', COUNT(*) AS '#checkouts'
        FROM ISSUED_BOOK
        WHERE ISSUED_BOOK.RETURN_DATE <= %s AND ISSUED_BOOK.RETURN_DATE > %s AND (ISSUED_BOOK.COUNT_EXTENSION = 0 OR ISSUED_BOOK.COUNT_EXTENSION IS NULL)
        GROUP BY USERNAME
        HAVING COUNT(*) > 1
        ORDER BY COUNT(*) DESC
        LIMIT 0, 5)'''

        cy = datetime.date.today().strftime("%Y")
        feb = datetime.date(int(cy), 2, 14)
        mar = datetime.date(int(cy), 3, 14)
        jan  = datetime.date(int(cy), 1, 14)
        r.cursor.execute(sql, (feb, jan, mar, feb))
        tuplelist = r.cursor.fetchall()
        
        labellist = []
        i = 2
        dic = {1:'Jan',2:'Feb'}
        
        for e in tuplelist:
            labellist.append(Label(r.root, text = dic[int(e[0])]).grid(row=i, column = 0))
            labellist.append(Label(r.root, text = e[1]).grid(row=i, column = 1))
            labellist.append(Label(r.root, text = e[2]).grid(row=i, column = 2))
            i += 1

    ##########################POPULAR BOOKS REPORT###############
    def popularReportScreen(r):
        
        r.popularreport = Toplevel(r.selectscreen)
        r.popularreport.configure(bg="gold")    #### I tried to seperate windows with 2 sperate Frames, but if label is in a Frame, Frame's bg color doesn't function. I searched but they say OSX doesn't support it. do you have any solution?

        label = Label(r.popularreport, text="popular book report", bg = "gold")
        label.grid(row = 0, columnspan = 4, padx = 5,pady=5,sticky=W)

        label2 = Label(r.popularreport, text="Month-----",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        label3 = Label(r.popularreport, text="Title-----",bg="gold").grid(row = 1, column = 1,padx=5, pady = 5,sticky = E)
        label4 = Label(r.popularreport, text="# of checkouts-",bg="gold").grid(row = 1, column = 2,padx=5, pady = 5,sticky = E)

        r.showReport()

    def showReport(r):
        r.connect()
        r.labellist = []
        sql = '''(
            SELECT MONTH( ISSUED_BOOK.DATE_OF_ISSUED) AS  'Month', BOOK_BOOK.TITLE, COUNT( * ) AS  '#checkouts'
            FROM BOOK_BOOK
            INNER JOIN ISSUED_BOOK ON BOOK_BOOK.ISBN = ISSUED_BOOK.ISBN
            AND ISSUED_BOOK.RETURN_DATE <=  %s AND ISSUED_BOOK.RETURN_DATE > %s
            WHERE ISSUED_BOOK.COUNT_EXTENSION =0
            OR ISSUED_BOOK.COUNT_EXTENSION IS NULL 
            GROUP BY TITLE
            ORDER BY COUNT( * ) DESC
            LIMIT 0, 3
            )
            UNION (
            
            SELECT MONTH( ISSUED_BOOK.DATE_OF_ISSUED) AS  'Month', BOOK_BOOK.TITLE, COUNT( * ) AS  '#checkouts'
            FROM BOOK_BOOK
            INNER JOIN ISSUED_BOOK ON BOOK_BOOK.ISBN = ISSUED_BOOK.ISBN
            AND ISSUED_BOOK.RETURN_DATE <= %s AND ISSUED_BOOK.RETURN_DATE > %s
            WHERE ISSUED_BOOK.COUNT_EXTENSION =0
            OR ISSUED_BOOK.COUNT_EXTENSION IS NULL 
            GROUP BY TITLE
            ORDER BY COUNT( * ) DESC
            LIMIT 0 , 3
            )
           '''
        cy = datetime.date.today().strftime("%Y")
        feb = datetime.date(int(cy), 2, 14)
        mar = datetime.date(int(cy), 3, 14)
        jan  = datetime.date(int(cy), 1, 14)
        r.cursor.execute(sql, (feb, jan, mar, feb))
        tuplelist = r.cursor.fetchall()

        labellist = []
        i = 2
        dic = {1:'Jan',2:'Feb',3:'Mar',4:'Apr',5:'May',6:'Jun',7:'Jul',8:'Aug',9:'Sep',10:'Oct',11:'Nov',12:'Dec'}
  
        for e in tuplelist:
            labellist.append(Label(r.root, text = dic[int(e[0])]).grid(row=i, column = 0))
            labellist.append(Label(r.root, text = e[1]).grid(row=i, column = 1))
            labellist.append(Label(r.root, text = e[2]).grid(row=i, column = 2))
            i += 1
    #####################POPULAR SUBJECT REPORT ####################
    def popularSubjectScreen(r):
        
        r.popularsubject = Toplevel(r.selectscreen)
        r.popularsubject.configure(bg="gold")    #### I tried to seperate windows with 2 sperate Frames, but if label is in a Frame, Frame's bg color doesn't function. I searched but they say OSX doesn't support it. do you have any solution?

        label = Label(r.popularsubject, text="popular book report", bg = "gold")
        label.grid(row = 0, columnspan = 4, padx = 5,pady=5,sticky=W)

        label2 = Label(r.popularsubject, text="Month-----",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        label3 = Label(r.popularsubject, text="Top Subjects-----",bg="gold").grid(row = 1, column = 1,padx=5, pady = 5,sticky = E)
        label4 = Label(r.popularsubject, text="# of checkouts-",bg="gold").grid(row = 1, column = 2,padx=5, pady = 5,sticky = E)

        r.showPopularReport()

    def showPopularReport(r):
        r.connect()
        r.labellist = []
        sql = '''(
SELECT MONTH( ISSUED_BOOK.DATE_OF_ISSUED) AS  'Month', BOOK_BOOK.SUBJECT_NAME AS  'Top Subject', COUNT( * ) AS  '#checkouts'
FROM BOOK_BOOK
INNER JOIN ISSUED_BOOK ON BOOK_BOOK.ISBN = ISSUED_BOOK.ISBN
AND ISSUED_BOOK.RETURN_DATE <=  %s AND 
ISSUED_BOOK.RETURN_DATE >  %s
WHERE ISSUED_BOOK.COUNT_EXTENSION =0
OR ISSUED_BOOK.COUNT_EXTENSION IS NULL 
GROUP BY SUBJECT_NAME
ORDER BY COUNT( * ) DESC
)
UNION (

SELECT MONTH( ISSUED_BOOK.DATE_OF_ISSUED) AS  'Month', BOOK_BOOK.SUBJECT_NAME AS  'Top Subject', COUNT( * ) AS  '#checkouts'
FROM BOOK_BOOK
INNER JOIN ISSUED_BOOK ON BOOK_BOOK.ISBN = ISSUED_BOOK.ISBN
AND ISSUED_BOOK.RETURN_DATE <= %s 
AND ISSUED_BOOK.RETURN_DATE >  %s
WHERE ISSUED_BOOK.COUNT_EXTENSION =0
OR ISSUED_BOOK.COUNT_EXTENSION IS NULL 
GROUP BY SUBJECT_NAME
ORDER BY COUNT( * ) DESC
)
LIMIT 0 , 6'''

        cy = datetime.date(2010, 1, 1).strftime("%Y")
        ##cy = datetime.date.today().strftime("%Y")
        feb = datetime.date(int(cy), 2, 14)
        mar = datetime.date(int(cy), 3, 14)
        jan  = datetime.date(int(cy), 1, 14)
        r.cursor.execute(sql, (feb, jan, mar, feb))
        tuplelist = r.cursor.fetchall()
        
        labellist = []
        i = 2
        dic = {1:'Jan',2:'Feb'}
        
        for e in tuplelist:
            labellist.append(Label(r.root, text = dic[int(e[0])]).grid(row=i, column = 0))
            labellist.append(Label(r.root, text = e[1]).grid(row=i, column = 1))
            labellist.append(Label(r.root, text = e[2]).grid(row=i, column = 2))
            i += 1

        
Staff()
