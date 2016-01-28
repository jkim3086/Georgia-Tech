import pymysql
from tkinter import *
from time import gmtime, strftime
import datetime

class User:
    def __init__(r):
        
        r.root = Tk()
        r.root.configure(bg="gold")    #### I tried to seperate windows with 2 sperate Frames, but if label is in a Frame, Frame's bg color doesn't function. I searched but they say OSX doesn't support it. do you have any solution?

        label = Label(r.root, text="Login", bg = "gold")
        label.grid(row = 0, columnspan = 4, padx = 5,pady=5,sticky=W)

        label2 = Label(r.root, text="Username :",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.loginuserEntry = Entry(r.root, width = 30)
        r.loginuserEntry.grid(row = 1,column=1, columnspan = 3, sticky=W,padx=5,pady=5)
        label3 = Label(r.root, text="password :",bg="gold").grid(row = 2, column = 0,padx=5, pady = 5,sticky = E)
        r.loginpasswordEntry = Entry(r.root, width = 30)
        r.loginpasswordEntry.grid(row=2, column=1, columnspan = 3, padx=5,pady=5,sticky=W)

        button2 = Button(r.root,text="LogIn", command = r.searchBook).grid(row=3,column=4,sticky = W)
        button3 = Button(r.root, text="Registration", command = r.registrationScreen).grid(row = 3, column = 3, sticky=W)
        button4 = Button(r.root, text = "Track Location", command = r.trackLocationScreen).grid(row=3, column = 2, sticky=W)
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

    def registrationScreen(r):
        r.connect()
        r.register = Toplevel(r.root)
        r.root.withdraw()
        user = Label(r.register, text="Username :",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.username = Entry(r.register, width = 30)
        r.username.grid(row = 1,column=1, columnspan = 3, sticky=W,padx=5,pady=5)
        label3 = Label(r.register, text="password :",bg="gold").grid(row = 2, column = 0,padx=5, pady = 5,sticky = E)
        r.entry2 = Entry(r.register, width = 30)
        r.entry2.grid(row=2, column=1, columnspan = 3, padx=5,pady=5,sticky=W)
        label4 = Label(r.register, text="confirm password :",bg="gold").grid(row = 3, column = 0,padx=5, pady = 5,sticky = E)
        r.entry3 = Entry(r.register, width = 30)
        r.entry3.grid(row=3, column=1, columnspan = 3, padx=5,pady=5,sticky=W)
        button2 = Button(r.register,text="Register", command = r.registerUser).grid(row=4,column=4,sticky = W)

    def registerUser(r):
        ##user already exists?
        sql = "SELECT COUNT(USERNAME) FROM USER WHERE USERNAME = %s"
        r.cursor.execute(sql, r.username.get())
        if (r.cursor.fetchone()[0] >0 ):
            messagebox.showerror("error", "user already exists!")
        elif r.entry2.get() != r.entry3.get():
            ##passwords are same?
            messagebox.showerror("error", "password not same!")
        else:
            sql = "INSERT INTO USER VALUES(%s, %s)"
            r.cursor.execute(sql, (r.username.get(), r.entry2.get()))
            r.createProfile()
            r.db.commit()

    def createProfile(r):
        r.profile = Toplevel(r.register)
        r.register.withdraw()
        r.profile.deiconify()
        fn = Label(r.profile, text="First Name :",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.fn = Entry(r.profile, width = 30)
        r.fn.grid(row = 1,column=1, columnspan = 3, sticky=W,padx=5,pady=5)
        ln = Label(r.profile, text="Last Name :",bg="gold").grid(row = 1, column = 2,padx=5, pady = 5,sticky = E)
        r.ln = Entry(r.profile, width = 30)
        r.ln.grid(row=1, column=3, columnspan = 3, padx=5,pady=5,sticky=W)
        
        dob = Label(r.profile, text="DOB:",bg="gold").grid(row = 2, column = 0,padx=5, pady = 5,sticky = E)
        r.dob = Entry(r.profile, width = 30)
        r.dob.grid(row=2, column=1, columnspan = 3, padx=5,pady=5,sticky=W)
        gender = Label(r.profile, text="Gender:",bg="gold").grid(row =2, column=2,padx = 5, pady = 5,sticky = E)
        r.gender = Entry(r.profile, width = 30)
        r.gender.grid(row = 2,column=3, columnspan = 3, sticky=W,padx=5,pady=5)
        
        email = Label(r.profile, text="Email:",bg="gold").grid(row = 3, column = 0,padx=5, pady = 5,sticky = E)
        r.email = Entry(r.profile, width = 30)
        r.email.grid(row=3, column=1, padx=5,pady=5,sticky=W)
        
        fm = Label(r.profile, text="FACULTY MEMBER? ",bg="gold").grid(row = 3, column = 2,padx=5, pady = 5,sticky = E)
        r.yesorno = IntVar()
        r.fm = Checkbutton(r.profile, text = "yes", variable = r.yesorno)
        r.fm.grid(row=3, column=3, columnspan = 3, padx=5,pady=5,sticky=W)

        addr = Label(r.profile, text="ADDRESS:",bg="gold").grid(row =4, column=0,padx = 5, pady = 5,sticky = E)
        r.addr = Entry(r.profile, width = 30)
        r.addr.grid(row = 4,column=1, columnspan = 3, sticky=W,padx=5,pady=5)
        
        dept = Label(r.profile, text="DEPT:",bg="gold").grid(row = 4, column = 2,padx=5, pady = 5,sticky = E)

        r.v1 = StringVar()
        l = r.showDepartment()
        r.dept = OptionMenu(r.profile, r.v1, *l)
        r.dept.grid(row=4, column=3, columnspan = 3, padx=5,pady=5,sticky=W)

        button2 = Button(r.profile,text="Submit", command = r.uploadProfile).grid(row=5,column=4,sticky = W)

    def showDepartment(r):
        sql = "SELECT DISTINCT DEP FROM STUDENT_FACULTY"
        r.cursor.execute(sql)
        ll = r.cursor.fetchall()
        l = []
        for e in ll:
            l.append(e[0])
        return l
            

    def uploadProfile(r):
        sql = "INSERT INTO STUDENT_FACULTY(USERNAME, NAME,DOB, GENDER, EMAIL, ADDRESS, IS_FACULTY, DEP) VALUES(%s, %s, CAST(%s AS DATETIME), %s, %s, %s, %s, %s)"
        name = r.fn.get()+ " " + r.ln.get()
        
        selectdic = {1:'Y', 0:'N'}
        select = selectdic[r.yesorno.get()]
        
        r.cursor.execute(sql, (r.username.get() ,name, r.dob.get(), r.gender.get(), r.email.get(), r.addr.get(), select, r.v1.get()))
        r.db.commit()
        r.toLogin('profile')

    def toLogin(r, s):
        if s == 'searchBook':
            r.root.deiconify()
            r.searchbookScreen.withdraw()
        elif s == 'profile':
            r.root.deiconify()
            r.profile.withdraw()


    ###########################SEARCH BOOK#########################
    def searchBook(r):
        if(r.loginCheck()):
            r.searchbookScreen = Toplevel(r.root)
            r.root.withdraw()
            r.searchbookScreen.configure(background='gold')
            ##isbn, publisher, title, edition, author
            label = Label(r.searchbookScreen, text="Search Books")
            label.grid(row = 0, columnspan = 4, padx = 5,pady=5,sticky=W)
            ###############################At least one of ISBN, Title, Author required #########################################
            label2 = Label(r.searchbookScreen, text="ISBN :",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
            r.entry = Entry(r.searchbookScreen, width = 30)
            r.entry.grid(row = 1,column=1, columnspan = 3, sticky=W,padx=5,pady=5)
            
            label3 = Label(r.searchbookScreen, text="Title :",bg="gold").grid(row = 3, column = 0,padx=5, pady = 5,sticky = E)
            r.entry3 = Entry(r.searchbookScreen, width = 30)
            r.entry3.grid(row=3, column=1, columnspan = 3, padx=5,pady=5,sticky=W)
            
            label4 = Label(r.searchbookScreen, text="Author :",bg="gold").grid(row = 5, column = 0,padx=5, pady = 5,sticky = E)
            r.entry4 = Entry(r.searchbookScreen, width = 30)
            r.entry4.grid(row=5, column=1, columnspan = 3, padx=5,pady=5,sticky=W)

            button2 = Button(r.searchbookScreen,text="Search", command = r.searchCheck).grid(row=6,column=1,sticky = W)
            button3 = Button(r.searchbookScreen,text="Logout", command = lambda: r.toLogin('searchBook')).grid(row=6,column=4,sticky = W)
            button4 = Button(r.searchbookScreen, text = "Extend due",command = r.extensionScreen).grid(row=6,column=5,sticky = W)
    def searchCheck(r):
        title = r.entry3.get().strip()
        author = r.entry4.get().strip()
        isbn = r.entry.get().strip()
        l = []
        ## select   isbn, title, eidtion, on reserve
        if title == '' and author == '' and isbn == '':
            messagebox.showerror("error","enter information")
        elif author == '':
            isbn = int(isbn)
            sql = 'SELECT ISBN, TITLE, EDTION, IS_BOOK_ON_RESERVE FROM BOOK_BOOK WHERE TITLE = %s OR ISBN = %s'
            try:
                r.cursor.execute(sql, (title, isbn))
                l = r.cursor.fetchall() ##(isbn, title, edition, on_Reserve)
            except:
                messagebox.showerror('error',"error during select!")
        else:
            sql = "SELECT ISBN, TITLE, EDTION, IS_BOOK_ON_RESERVE FROM BOOK_BOOK NATURAL JOIN AUTHORS WHERE BOOK_BOOK.ISBN = AUTHORS.ISBN AND BOOK_BOOK.TITLE = %s OR BOOK_BOOK.ISBN = %s OR AUTHOR_NAME = %s" 
            try:
                r.cursor.execute(sql, (title, isbn, author))
                l = r.cursor.fetchall() ##(isbn, title, edition, on_Reserve)
            except:
                messagebox.showerror('error',"error during select!")

        if len(l) == 0:
            messagebox.showerror('error!',"enter valid information!")
        else:
            r.reserve = {}
            r.ISBNdic = {}  ## dictionary key: isbn, value: title, edition, n_of copies
            for e in l:
                if e[3] == 'Y':
                    r.reserve[e[0]] = [e[1], e[2]]
                else:
                    r.ISBNdic[e[0]] = [e[1], e[2]] ## key is isbn, values are title and edition
            r.ISBNkeys = r.ISBNdic.keys()
            r.reserveKeys = r.reserve.keys()
            if len(r.ISBNkeys) > 0:
                for k in r.ISBNkeys:
                    sql = 'SELECT COUNT(COPY_N) FROM BOOK_COPY WHERE ISBN = %s AND IS_CHECKED_OUT = %s AND IS_ON_HOLD = %s'
                    r.cursor.execute(sql, (k, 'N','N'))
                    number = r.cursor.fetchone()[0]
                    r.ISBNdic[k].append(number) ## number is tuple so i need index, values are title, edition, number of available books
            if len(r.reserveKeys) > 0:
                for k in r.reserveKeys:
                    sql = 'SELECT COUNT(COPY_N) FROM BOOK_COPY WHERE ISBN = %s'
                    r.cursor.execute(sql, k)
                    number = r.cursor.fetchone()[0]
                    r.reserve[k].append(number) ## number is tuple so i need index, values are title, edition, number of available books

            r.holdRequest()


    ########################HOLD REQUEST###################################
    def holdRequest(r):  ## ISBN, TITLE, EDITION, N OF AVAILABLE   ### CHECK ON_RESERVE LATER

        r.holdrequest = Toplevel(r.searchbookScreen)
        r.searchbookScreen.withdraw()
        r.holdrequest.configure(bg='gold')
        keylist = list(r.ISBNkeys)
        if len(keylist) > 0:
            
            r.value = keylist[0]
            label = Label(r.holdrequest, text = "Hold request", bg='gold').grid(row=0, column = 0)
            ## store radiobutton at r.ISBNdic first , and grid it later        ## get each tuple and store it at dict, use it for value
            label2 = Label(r.holdrequest, text="SELECT ", bg='gold').grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
            label3 = Label(r.holdrequest, text="ISBN---", bg='gold').grid(row =1, column=1,padx = 5, pady = 5,sticky = E)
            label4 = Label(r.holdrequest, text="TITLE---", bg='gold').grid(row =1, column=2,padx = 5, pady = 5,sticky = E)
            label5 = Label(r.holdrequest, text="EDITION---", bg='gold').grid(row =1, column=3,padx = 5, pady = 5,sticky = E)
            label6 = Label(r.holdrequest, text="#copies available:", bg='gold').grid(row =1, column=4,padx = 5, pady = 5,sticky = E)
            


            labellist = []
            ## get each Label
            for k in keylist:
                l = []
                l.append(Radiobutton(r.holdrequest, text='', variable=r.value, value = k, bg='gold')) ##radiobutton
                l.append(Label(r.holdrequest, text=k, bg='gold')) ##isbn
                l.append(Label(r.holdrequest, text=r.ISBNdic[k][0], bg='gold')) #title
                l.append(Label(r.holdrequest, text=r.ISBNdic[k][1], bg='gold'))#edition
                l.append(Label(r.holdrequest, text=r.ISBNdic[k][2], bg='gold')) #n of available
                labellist.append(l)
                                    
            ## grid all the items
            y = 2
            if(len(labellist) > 4):
                for i in range(0,4):
                    labellist[i][0].grid(row= (i+2),column=0)
                    labellist[i][1].grid(row=(i+2),column=1)
                    labellist[i][2].grid(row=(i+2),column=2)
                    labellist[i][3].grid(row=(i+2),column=3)
                    labellist[i][4].grid(row=(i+2), column=4)
            else:
                for e in labellist:
                    e[0].grid(row=y,column=0)
                    e[1].grid(row=y,column=1)
                    e[2].grid(row=y,column=2)
                    e[3].grid(row=y,column=3)
                    e[4].grid(row=y, column=4)
                    y += 1

            ##date
            r.currentDate = datetime.date.today().isoformat()
            r.returnDate = (datetime.date.today() + datetime.timedelta(17)).isoformat()
            
            entry = Entry(r.holdrequest, text=r.currentDate, width = 10)
            entry.grid(row=6, column=0) ##hold request date
            entry2 = Entry(r.holdrequest, text=r.returnDate, width = 10)
            entry2.grid(row=6, column = 2) ##estimated return date

            entry.insert(0, r.currentDate)
            entry.configure(state="readonly")
            entry2.insert(0,r.returnDate)
            entry2.configure(state="readonly")
            button = Button(r.holdrequest, text='submit', command = r.issuebook).grid(row=7, column = 2)
            ##when N-of copies available is 0, cannot hold request


        ###RESERVE BOOKS
        reserveKeylist = list(r.reserveKeys)
        z = 10
        if len(reserveKeylist) > 0:
            keylabellist = []
            label10 = Label(r.holdrequest, text = "RESERVE BOOKS").grid(row =8, column=1,padx = 5, pady = 5,sticky = E)
            label11 = Label(r.holdrequest, text="ISBN---").grid(row =9, column=0,padx = 5, pady = 5,sticky = E)
            label12 = Label(r.holdrequest, text="TITLE---").grid(row =9, column=1,padx = 5, pady = 5,sticky = E)
            label13 = Label(r.holdrequest, text="EDITION---").grid(row =9, column=2,padx = 5, pady = 5,sticky = E)
            label14 = Label(r.holdrequest, text="#copies available:").grid(row =9, column=3,padx = 5, pady = 5,sticky = E)
            for k in reserveKeylist:
                l = []
                l.append(Label(r.holdrequest, text=k, bg='gold')) ##isbn
                l.append(Label(r.holdrequest, text=r.reserve[k][0], bg='gold')) #title
                l.append(Label(r.holdrequest, text=r.reserve[k][1], bg='gold'))#edition
                l.append(Label(r.holdrequest, text=r.reserve[k][2], bg='gold')) #n of available
                keylabellist.append(l)
            if len(reserveKeylist) > 2 :
                for i in range(0,2):
                    keylabellist[i][0].grid(row=(i+10),column=0)
                    keylabellist[i][1].grid(row=(i+10),column=1)
                    keylabellist[i][2].grid(row=(i+10),column=2)
                    keylabellist[i][3].grid(row=(i+10),column=3)
            else:
                for e in keylabellist:
                    e[0].grid(row=z,column=0)
                    e[1].grid(row=z,column=1)
                    e[2].grid(row=z,column=2)
                    e[3].grid(row=z,column=3)
                    z += 1
        tosearch = Button(r.holdrequest, text= 'cancel', command = r.toSearch).grid(row = 20, column = 5)
        tofuturehold = Button(r.holdrequest,text='futurehold', command = r.futureHoldScreen).grid(row = 20, column = 4)

        
    def toSearch(r):
        r.holdrequest.withdraw()
        r.searchbookScreen.deiconify()

    def issuebook(r):
        ##update issed book
        isbn = r.value
        copy_n = r.ISBNdic[isbn][2]
        if copy_n == 0:
            messagebox.showerror("error!", "none of books is available")
        else:
            issued_id = hash('isbn') % 1000000000
            r.currentDate = datetime.date.today()
            r.currentDate = r.currentDate.strftime('%Y-%m-%d %H:%M:%S')

            r.returnDate = datetime.date.today() + datetime.timedelta(17)
            r.returnDate = r.returnDate.strftime('%Y-%m-%d %H:%M:%S')

            sql = 'INSERT INTO ISSUED_BOOK(USERNAME, COPY_N, ISSUED_ID, DATE_OF_ISSUED, RETURN_DATE, COUNT_EXTENSION, ISBN) VALUES(%s, %s, %s,%s,%s,%s,%s)' ## user,copy,id,date,red,count,isbn
            r.cursor.execute(sql, (r.loginuser, copy_n, issued_id, r.currentDate, r.returnDate, 0, isbn))
            ##update book copy
            print('inserted')
            sql = 'UPDATE BOOK_COPY SET IS_ON_HOLD = %s WHERE ISBN = %s AND COPY_N = %s'
            r.cursor.execute(sql, ('Y', isbn, copy_n))
            print('updated')
            r.db.commit()
        r.toSearch()

    ##############################REQUEST EXTENSION############################
    def extensionScreen(r):
        r.extensionscreen = Toplevel(r.searchbookScreen)
        r.searchbookScreen.withdraw()
        
        r.count = -1
        
        label2 = Label(r.extensionscreen, text="Enter your issue_id ",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.entry = Entry(r.extensionscreen, width = 10)
        r.entry.grid(row=1, column=1)

        exdlabel = Label(r.extensionscreen, text="original extension date",bg="gold").grid(row =2, column=0,padx = 5, pady = 5,sticky = E)
        r.exdentry = Entry(r.extensionscreen, width = 10, state="readonly")
        r.exdentry.grid(row=2, column=1)
        redlabel = Label(r.extensionscreen, text="original return date",bg="gold").grid(row =2, column=2,padx = 5, pady = 5,sticky = E)
        r.redentry = Entry(r.extensionscreen, width = 10, state="readonly")
        r.redentry.grid(row=2, column=3)

        newexdlabel = Label(r.extensionscreen, text="new extension date",bg="gold").grid(row = 3, column=0,padx = 5, pady = 5,sticky = E)
        r.newexdentry = Entry(r.extensionscreen, width = 10, state="readonly")
        r.newexdentry.grid(row=3, column=1)
        newredlabel = Label(r.extensionscreen, text="new return date",bg="gold").grid(row =3, column=2,padx = 5, pady = 5,sticky = E)
        r.newredentry = Entry(r.extensionscreen, width = 10, state="readonly")
        r.newredentry.grid(row=3, column=3)

        
        button = Button(r.root, text="submit", command = r.searchDate).grid(row=1,column=2)
        button = Button(r.root, text="submit", command = r.requestExtension).grid(row=4,column=2)


        r.root.mainloop()

    def searchDate(r):

        r.issed_id = r.entry.get()
        ##CHECK THAT BOOK IS NOT ON FUTURE HOLD
        sql = "SELECT ISBN, COPY_N FROM ISSUED_BOOK WHERE ISSUED_ID = %s"
        r.cursor.execute(sql, r.issed_id)
        isbnAndcopy_n = r.cursor.fetchone()
        isbn = isbnAndcopy_n[0]
        copy_n = isbnAndcopy_n[1]
        sql = "SELECT FUTURE_REQUESTER FROM BOOK_COPY WHERE ISBN = %s AND COPY_N = %s"
        r.cursor.execute(sql, (isbn, copy_n))
        reqTuple = r.cursor.fetchone()[0]
        if (reqTuple == None):
            ##COUNT_EXTENSION
            sql = "SELECT COUNT_EXTENSION FROM ISSUED_BOOK WHERE ISSUED_ID = %s" 
            r.cursor.execute(sql, r.issed_id)
            r.countTuple = r.cursor.fetchone()
            if(r.countTuple[0] == None):
                r.count = 0
                print(r.count)
            else:
                r.count = r.countTuple[0]
                print(r.count)
            if r.count <= 2 and r.count >= 0:
                ## select original checkout date, current extension date. return date
                sql = "SELECT EXTENSION_DATE, RETURN_DATE FROM ISSUED_BOOK WHERE ISSUED_ID = %s"
                r.cursor.execute(sql, r.issed_id)
                t = r.cursor.fetchone()
                exd = t[0]
                red = t[1]
                if(exd != None):
                    r.exdentry.configure(state=NORMAL)
                    r.exdentry.insert(0, exd.isoformat())
                    r.exdentry.configure(state=DISABLED)
                r.redentry.configure(state=NORMAL)
                r.redentry.insert(0, red.isoformat())
                r.redentry.configure(state=DISABLED)
                                     
                r.newexd = datetime.date.today()
                r.newred = datetime.date.today() + datetime.timedelta(14)                             
                r.newexdentry.configure(state=NORMAL)
                r.newexdentry.insert(0, r.newexd.isoformat())
                r.newexdentry.configure(state=DISABLED)
                r.newredentry.configure(state=NORMAL)
                r.newredentry.insert(0, r.newred.isoformat())
                r.newredentry.configure(state=DISABLED)
        else:
           messagebox.showerror('error!', 'already on future hold!')
    def requestExtension(r):
        if r.count < 2 and r.count >= 0:
            newexd = str(r.newexd)
            newred = str(r.newred)
            ##update extension date, new return date, count_extension
            sql = "UPDATE ISSUED_BOOK SET EXTENSION_DATE = CAST(%s AS DATETIME), RETURN_DATE = CAST(%s AS DATETIME), COUNT_EXTENSION = %s WHERE ISSUED_ID = %s;"
            r.cursor.execute(sql, (newexd, newred, (r.count + 1), r.issed_id))
            r.db.commit()
            r.db.close()
        elif r.count == -1:
            messagebox.showerror('error!', 'please enter valid issue id first!')
        else:
            messagebox.showerror('error!', 'you exceeded maximum number of extension!')

    ####################FUTURE HOLD#######################
    def futureHoldScreen(r):
        r.future = Toplevel(r.holdrequest)
        r.holdrequest.withdraw()
        r.count = -1
        
        label2 = Label(r.future, text="isbn",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.entry = Entry(r.future, width = 10)
        r.entry.grid(row=1, column=1)

        button = Button(r.future, text="request", command = r.request).grid(row = 1, column = 2)
        
        label3 = Label(r.future, text="Copy Number",bg="gold").grid(row =2, column=0,padx = 5, pady = 5,sticky = E)
        r.entry2 = Entry(r.future, width = 10, state=DISABLED)
        r.entry2.grid(row=2, column=1)

        label4 = Label(r.future, text="Expected Available Date",bg="gold").grid(row =3, column=0,padx = 5, pady = 5,sticky = E)
        r.entry3 = Entry(r.future, width = 10, state=DISABLED)
        r.entry3.grid(row=3, column=1)
        button2 = Button(r.future, text="OK", command = r.ok).grid(row = 3, column = 2)
        r.root.mainloop()

    def request(r):
        isbn = r.entry.get()
        sql = "SELECT COPY_N, RETURN_DATE FROM ISSUED_BOOK NATURAL JOIN BOOK_COPY WHERE ISSUED_BOOK.ISBN = BOOK_COPY.ISBN AND ISSUED_BOOK.ISBN = %s AND BOOK_COPY.FUTURE_REQUESTER = NULL"
        r.cursor.execute(sql, isbn)
        booktuple = r.cursor.fetchall()
        mincopy_n = booktuple[0][0]
        mindate = booktuple[0][1].date()

        for e in booktuple: ##find min date
            comparedate = e[0][1].date()
            if comparedate < mindate:
                mindate = comparedate
                mincopy_n = e[0][0]
                
        r.entry2.configure(state=NORMAL)
        r.entry2.insert(0, str(mincopy_n))
        r.entry2.configure(state=DISABLED)
        
        r.entry3.configure(state=NORMAL)
        r.entry3.insert(0, str(mindate))
        r.entry3.configure(state=DISABLED)
    def ok(r):
        copy_n = r.entry2.get()
        isbn = r.entry.get()
        sql = "UPDATE BOOK_COPY SET FUTURE_REQUESTER = %s WHERE COPY_N = %s AND ISBN = %s"
        r.cursor.execute(sql, (r.username, copy_n, isbn))
        r.db.commit()

    ########################track location##################
    def trackLocationScreen(r):
        
        r.tracklocation = Toplevel(r.root)
        r.tracklocation.configure(bg="gold")    #### I tried to seperate windows with 2 sperate Frames, but if label is in a Frame, Frame's bg color doesn't function. I searched but they say OSX doesn't support it. do you have any solution?

        label = Label(r.tracklocation, text="Track Location", bg = "gold")
        label.grid(row = 0, padx = 5,pady=5,sticky=W)

        isbn = Label(r.tracklocation, text="ISBN :",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.isbn = Entry(r.tracklocation, width = 30)
        r.isbn.grid(row = 1,column=1, sticky=W,padx=5,pady=5)
        
        fn = Label(r.tracklocation, text="Floor Number",bg="gold").grid(row = 2, column = 0,padx=5, pady = 5,sticky = E)
        r.fn = Entry(r.tracklocation, width = 30)
        r.fn.grid(row=2, column=1, padx=5,pady=5,sticky=W)
        sn = Label(r.tracklocation, text="Shelf Number :",bg="gold").grid(row = 2, column = 2,padx=5, pady = 5,sticky = E)
        r.sn = Entry(r.tracklocation, width = 30)
        r.sn.grid(row=2, column=3, padx=5,pady=5,sticky=W)
        an = Label(r.tracklocation, text="Aisle Number",bg="gold").grid(row = 3, column = 0,padx=5, pady = 5,sticky = E)
        r.an = Entry(r.tracklocation, width = 30)
        r.an.grid(row=3, column=1, padx=5,pady=5,sticky=W)
        sub = Label(r.tracklocation, text="Subject",bg="gold").grid(row = 3, column = 2,padx=5, pady = 5,sticky = E)
        r.sub = Entry(r.tracklocation, width = 30)
        r.sub.grid(row=3, column=3, padx=5,pady=5,sticky=W)


        button2 = Button(r.tracklocation,text="locate", command = r.findbook).grid(row=1,column=2,sticky = W)

    def findbook(r):
        r.connect()
        sql = '''SELECT SUBJECT_NAME AS  'Subject', SHELF.SHELF_N AS 'Shelf Number',
        SHELF.FLOOR_N AS  'Floor Number', SHELF.AISLE_N AS  'Aisle Number', ISBN= %s
        FROM BOOK_BOOK AS BB, SHELF
        WHERE BB.SHELF_N = SHELF.SHELF_N
        LIMIT 0 , 30'''
        r.cursor.execute(sql, r.isbn.get())##sub, sn, fn, an
        l = r.cursor.fetchone()
        
        r.sub.configure(state=NORMAL)
        r.sub.insert(0, l[0])
        r.sub.configure(state=DISABLED)
        r.sn.configure(state=NORMAL)
        r.sn.insert(0, l[1])
        r.sn.configure(state=DISABLED)
        r.fn.configure(state=NORMAL)
        r.fn.insert(0, l[2])
        r.fn.configure(state=DISABLED)
        r.an.configure(state=NORMAL)
        r.an.insert(0, l[3])
        r.an.configure(state=DISABLED)
        
        
User()
