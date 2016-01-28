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

        button2 = Button(r.root,text="LogIn", command = r.loginCheck).grid(row=3,column=4,sticky = W)
        button3 = Button(r.root, text="Registration", command = r.loginCheck).grid(row = 3, column = 3, sticky=W)
        r.root.mainloop()

    def connect(r):
        try:
            r.db = pymysql.connect(host = 'academic-mysql.cc.gatech.edu', passwd = 'LeQs1rXt' , user = 'cs4400_Group_75', db='cs4400_Group_75')
            r.cursor = r.db.cursor()
        except:
            return None
    '''

    def loginCheck(r):
        r.loginuser = str(r.loginuserEntry.get())
        password = r.loginpasswordEntry.get()
        r.connect()
        if(r.cursor != None):
            sql = 'SELECT COUNT(USERNAME) FROM USER WHERE USERNAME = %s AND PASSWORD = %s'
            try:
                r.cursor.execute(sql, (r.loginuser, password))
                e = r.cursor.fetchone()[0]
                if(e == 1):
                    sql = "SELECT COUNT(USERNAME) FROM STAFF WHERE USERNAME = %s"
                    r.cursor.execute(sql, r.loginuser)
                    if r.cursor.fetchone()[0] == 1:
                        selectScreen()
                    else:
                        searchBook()
                else:
                    messagebox.showerror('error!', 'Wrong username or password')
            except:
                messagebox.showerror('error!', 'Some Error occured sorry')
        else:
            print('not connected')
        return False'''
    def loginCheck(r):
        r.loginuser = str(r.loginuserEntry.get())
        password = r.loginpasswordEntry.get()
        r.connect()
        if(r.cursor != None):
            sql = 'SELECT COUNT(USERNAME) FROM USER WHERE USERNAME = %s AND PASSWORD = %s'

            r.cursor.execute(sql, (r.loginuser, password))
            e = r.cursor.fetchone()[0]
            if(e == 1):
                sql = "SELECT COUNT(USERNAME) FROM STAFF WHERE USERNAME = %s"
                r.cursor.execute(sql, r.loginuser)
                if r.cursor.fetchone()[0] == 1:
                    r.selectScreen()
                else:
                    r.searchBook()
            else:
                messagebox.showerror('error!', 'Wrong username or password')
        else:
            print('not connected')
        return False
    ############################REGISTRATION######################################
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
            r.loginuserEntry.delete(0, 'end')
            r.loginpasswordEntry.delete(0, 'end' )
            r.root.deiconify()
            r.searchbookScreen.withdraw()
        elif s == 'profile':
            r.loginuserEntry.delete(0, 'end')
            r.loginpasswordEntry.delete(0, 'end' )
            r.root.deiconify()
            r.profile.withdraw()
        elif s == 'selectscreen':
            r.loginuserEntry.delete(0, 'end')
            r.loginpasswordEntry.delete(0, 'end' )
            r.root.deiconify()
            r.selectscreen.withdraw()

    ###########################SEARCH BOOK#########################
    def searchBook(r):
        r.searchbookScreen = Toplevel(r.root)
        r.root.withdraw()
        r.searchbookScreen.configure(background='gold')
        ##isbn, publisher, title, edition, author
        label = Label(r.searchbookScreen, text="Search Books")
        label.grid(row = 0, columnspan = 4, padx = 5,pady=5,sticky=W)
        ###############################At least one of ISBN, Title, Author required #########################################
        label2 = Label(r.searchbookScreen, text="ISBN :",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.searchbookentry1 = Entry(r.searchbookScreen, width = 30)
        r.searchbookentry1.grid(row = 1,column=1, columnspan = 3, sticky=W,padx=5,pady=5)
        
        label3 = Label(r.searchbookScreen, text="Title :",bg="gold").grid(row = 3, column = 0,padx=5, pady = 5,sticky = E)
        r.searchbookentry2 = Entry(r.searchbookScreen, width = 30)
        r.searchbookentry2.grid(row=3, column=1, columnspan = 3, padx=5,pady=5,sticky=W)
        
        label4 = Label(r.searchbookScreen, text="Author :",bg="gold").grid(row = 5, column = 0,padx=5, pady = 5,sticky = E)
        r.searchbookentry3 = Entry(r.searchbookScreen, width = 30)
        r.searchbookentry3.grid(row=5, column=1, columnspan = 3, padx=5,pady=5,sticky=W)

        button2 = Button(r.searchbookScreen,text="Search", command = r.searchCheck).grid(row=6,column=1,sticky = W)
        button3 = Button(r.searchbookScreen,text="Logout", command = lambda: r.toLogin('searchBook')).grid(row=6,column=4,sticky = W)
        button4 = Button(r.searchbookScreen, text = "Extend due",command = r.extensionScreen).grid(row=6,column=5,sticky = W)
        button5 = Button(r.searchbookScreen, text = "Track Location", command = r.trackLocationScreen).grid(row=6, column = 6, sticky=W)

    def searchCheck(r):
        title = r.searchbookentry2.get().strip()
        author = r.searchbookentry3.get().strip()
        isbn = r.searchbookentry1.get().strip()
        l = []
        ## select   isbn, title, eidtion, on reserve
        if title == '' and author == '' and isbn == '':
            messagebox.showerror("error","enter information")
        elif author == '':
            isbn = isbn
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
            print(r.currentDate)
            entry.grid(row=6, column=0) ##hold request date
            entry2 = Entry(r.holdrequest, text=r.returnDate, width = 10)
            entry2.grid(row=6, column = 2) ##estimated return date

            entry.delete(0,'end')
            entry.insert(0, r.currentDate)
            entry.configure(state="readonly")
            entry2.delete(0,'end')
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
        r.clearEntry('hold')
        r.tosearch = Button(r.holdrequest, text= 'cancel', command = lambda: r.toSearch('hold')).grid(row = 20, column = 5)
        tofuturehold = Button(r.holdrequest,text='futurehold', command = r.futureHoldScreen).grid(row = 20, column = 4)

    def clearEntry(r, s):
        if s == 'hold':
            r.searchbookentry1.delete(0,'end')
            r.searchbookentry2.delete(0,'end')
            r.searchbookentry3.delete(0,'end')
        elif s == 'futurehold':
            r.futureholdentry1.delete(0,'end')
            r.futureholdentry2.delete(0,'end')
            r.futureholdentry3.delete(0,'end')
    
    def toSearch(r, s):
        if s == 'hold':
            r.holdrequest.withdraw()
            r.searchbookScreen.deiconify()
        elif s == 'futurehold':
            r.future.withdraw()
            r.searchbookScreen.deiconify()
        elif s == 'extension':
            r.extensionscreen.withdraw()
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
        r.toSearch('hold')

    ##############################REQUEST EXTENSION############################
    def extensionScreen(r):
        r.extensionscreen = Toplevel(r.searchbookScreen)
        r.searchbookScreen.withdraw()
        
        r.count = -1
        
        label2 = Label(r.extensionscreen, text="Enter your issue_id ",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.extensionentry = Entry(r.extensionscreen, width = 10)
        r.extensionentry.grid(row=1, column=1)

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

        
        button = Button(r.extensionscreen, text="submit", command = r.searchDate).grid(row=4,column=2)
        button2 = Button(r.extensionscreen, text="request Extension", command = r.requestExtension).grid(row=4,column=3)
        button3 = Button(r.extensionscreen, text="cancel", command = lambda: r.toSearch("extension")).grid(row=4,column=4)
        

    def searchDate(r):

        r.issed_id = r.extensionentry.get()
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
            messagebox.showerror('successful!','the book is extended!')
        elif r.count == -1:
            messagebox.showerror('error!', 'please enter valid issue id first!')
        else:
            messagebox.showerror('error!', 'you exceeded maximum number of extension!')

        r.extensionentry.delete(0, 'end')
        r.exdentry.config(state=NORMAL)
        r.exdentry.delete(0, 'end')
        r.exdentry.config(state=DISABLED)
        r.redentry.config(state=NORMAL)
        r.redentry.delete(0, 'end')
        r.redentry.config(state=DISABLED)
        r.newexdentry.config(state=NORMAL)
        r.newexdentry.delete(0, 'end')
        r.newexdentry.config(state=DISABLED)
        r.newredentry.config(state=NORMAL)
        r.newredentry.delete(0, 'end')
        r.newredentry.config(state=DISABLED)



    ####################FUTURE HOLD#######################
    def futureHoldScreen(r):
        r.future = Toplevel(r.holdrequest)
        r.holdrequest.withdraw()
        r.count = -1
        r.canrequestfuturehold = False
        label2 = Label(r.future, text="isbn",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.futureholdentry1 = Entry(r.future, width = 10)
        r.futureholdentry1.grid(row=1, column=1)

        button = Button(r.future, text="request", command = r.request).grid(row = 1, column = 2)
        
        label3 = Label(r.future, text="Copy Number",bg="gold").grid(row =2, column=0,padx = 5, pady = 5,sticky = E)
        r.futureholdentry2 = Entry(r.future, width = 10, state=DISABLED)
        r.futureholdentry2.grid(row=2, column=1)

        label4 = Label(r.future, text="Expected Available Date",bg="gold").grid(row =3, column=0,padx = 5, pady = 5,sticky = E)
        r.futureholdentry3 = Entry(r.future, width = 10, state=DISABLED)
        r.futureholdentry3.grid(row=3, column=1)
        button2 = Button(r.future, text="OK", command = r.futureHoldRequest).grid(row = 3, column = 2)
        button3 = Button(r.future, text="to Search", command = lambda: r.toSearch('futurehold')).grid(row = 3, column = 3)
      

    def request(r):
        isbn = r.futureholdentry1.get()
        ##check it is reserve book
        sql = "SELECT IS_BOOK_ON_RESERVE FROM BOOK_BOOK WHERE ISBN = %s"
        r.cursor.execute(sql, isbn)
        re = r.cursor.fetchone()
        if re[0] == 'Y':
            messagebox.showerror('error!', 'book is on reserve!')
        else:
            sql = "SELECT COPY_N, RETURN_DATE FROM ISSUED_BOOK NATURAL JOIN BOOK_COPY WHERE ISSUED_BOOK.ISBN = BOOK_COPY.ISBN AND ISSUED_BOOK.ISBN = %s AND BOOK_COPY.FUTURE_REQUESTER IS NULL"
            r.cursor.execute(sql, isbn)
            booktuple = r.cursor.fetchall()
            if len(booktuple) == 0:
                messagebox.showerror('error!', 'there is no book you can request for future hold now')
            else:
                mincopy_n = booktuple[0][0]
                mindate = booktuple[0][1]
                for e in booktuple: ##find min date
                    comparedate = e[1]
                    if comparedate < mindate:
                        mindate = comparedate
                        mincopy_n = e[0]
                        
                r.futureholdentry2.configure(state=NORMAL)
                r.futureholdentry2.insert(0, str(mincopy_n))
                r.futureholdentry2.configure(state=DISABLED)
                
                r.futureholdentry3.configure(state=NORMAL)
                r.futureholdentry3.insert(0, str(mindate))
                r.futureholdentry3.configure(state=DISABLED)
                r.canrequestfuturehold = True
            
    def futureHoldRequest(r):
        if r.canrequestfuturehold:
            copy_n = r.futureholdentry2.get()
            isbn = r.futureholdentry1.get()
            sql = "UPDATE BOOK_COPY SET FUTURE_REQUESTER = %s WHERE COPY_N = %s AND ISBN = %s"
            r.cursor.execute(sql, (r.loginuser, copy_n, isbn))
            r.db.commit()
            r.canrequestfuturehold = False
            messagebox.showerror('success!', 'your request is successfully done!')
            r.clearEntry('futurehold')
            r.toSearch('futurehold')
        
                         
    ########################track location##################
    def trackLocationScreen(r):
        
        r.tracklocation = Toplevel(r.root)
        r.tracklocation.configure(bg="gold")    #### I tried to seperate windows with 2 sperate Frames, but if label is in a Frame, Frame's bg color doesn't function. I searched but they say OSX doesn't support it. do you have any solution?

        label = Label(r.tracklocation, text="Track Location", bg = "gold")
        label.grid(row = 0, padx = 5,pady=5,sticky=W)

        isbn = Label(r.tracklocation, text="ISBN :",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.tracklocationentry1 = Entry(r.tracklocation, width = 30)
        r.tracklocationentry1.grid(row = 1,column=1, sticky=W,padx=5,pady=5)
        
        fn = Label(r.tracklocation, text="Floor Number",bg="gold").grid(row = 2, column = 0,padx=5, pady = 5,sticky = E)
        r.tracklocationentry2 = Entry(r.tracklocation, width = 30,state=DISABLED)
        r.tracklocationentry2.grid(row=2, column=1, padx=5,pady=5,sticky=W)
        sn = Label(r.tracklocation, text="Shelf Number :",bg="gold").grid(row = 2, column = 2,padx=5, pady = 5,sticky = E)
        r.tracklocationentry3 = Entry(r.tracklocation, width = 30,state=DISABLED)
        r.tracklocationentry3.grid(row=2, column=3, padx=5,pady=5,sticky=W)
        an = Label(r.tracklocation, text="Aisle Number",bg="gold").grid(row = 3, column = 0,padx=5, pady = 5,sticky = E)
        r.tracklocationentry4 = Entry(r.tracklocation, width = 30,state=DISABLED)
        r.tracklocationentry4.grid(row=3, column=1, padx=5,pady=5,sticky=W)
        sub = Label(r.tracklocation, text="Subject",bg="gold").grid(row = 3, column = 2,padx=5, pady = 5,sticky = E)
        r.tracklocationentry5 = Entry(r.tracklocation, width = 30,state=DISABLED)
        r.tracklocationentry5.grid(row=3, column=3, padx=5,pady=5,sticky=W)


        button2 = Button(r.tracklocation,text="locate", command = r.findbook).grid(row=1,column=2,sticky = W)

    def findbook(r):
        r.tracklocationentry5.config(state=NORMAL)
        r.tracklocationentry5.delete(0, 'end')
        r.tracklocationentry5.config(state=DISABLED)
        r.tracklocationentry3.config(state=NORMAL)
        r.tracklocationentry3.delete(0, 'end')
        r.tracklocationentry3.config(state=DISABLED)
        r.tracklocationentry2.config(state=NORMAL)
        r.tracklocationentry2.delete(0, 'end')
        r.tracklocationentry2.config(state=DISABLED)
        r.tracklocationentry4.config(state=NORMAL)
        r.tracklocationentry4.delete(0, 'end')
        r.tracklocationentry4.config(state=DISABLED)
        
        sql = '''SELECT SUBJECT_NAME AS  'Subject', SHELF.SHELF_N AS 'Shelf Number',
        SHELF.FLOOR_N AS  'Floor Number', SHELF.AISLE_N AS  'Aisle Number'
        FROM BOOK_BOOK AS BB, SHELF
        WHERE BB.SHELF_N = SHELF.SHELF_N AND ISBN = %s
        LIMIT 0 , 30'''
        r.cursor.execute(sql, r.tracklocationentry1.get())##sub, sn, fn, an
        l = r.cursor.fetchone()
        r.db.commit()
        r.tracklocationentry5.config(state=NORMAL)
        r.tracklocationentry5.insert(0, l[0])
        r.tracklocationentry5.config(state=DISABLED)
        r.tracklocationentry3.config(state=NORMAL)
        r.tracklocationentry3.insert(0, l[1])
        r.tracklocationentry3.config(state=DISABLED)
        r.tracklocationentry2.config(state=NORMAL)
        r.tracklocationentry2.insert(0, l[2])
        r.tracklocationentry2.config(state=DISABLED)
        r.tracklocationentry4.config(state=NORMAL)
        r.tracklocationentry4.insert(0, l[3])
        r.tracklocationentry4.config(state=DISABLED)
        
    ######################################################################
    ######################################################################
    ##########################     STAFF     #############################
    ######################################################################
    ######################################################################


    def selectScreen(r):
        r.selectscreen = Toplevel(r.root)
        r.root.withdraw()
        label = Label(r.selectscreen, text = "SELECT SCREEN").grid(row = 0, column = 0, columnspan = 4)
        checkout = Button(r.selectscreen, text="Checkout Screen", command = r.checkOutScreen).grid(row=1,column=0,sticky = W)
        returnbook = Button(r.selectscreen, text="Return book Screen", command = r.returnBookScreen).grid(row=2,column = 0,sticky=W)
        penalty = Button(r.selectscreen, text="Damaged/Lost book Screen", command = r.penaltyScreen).grid(row=3,column = 0, sticky=W)
        damagedbookreport = Button(r.selectscreen, text="Damaged book report", command = r.damagedBookScreen).grid(row=4, column = 0, sticky=W)
        frequentreport = Button(r.selectscreen, text="frequent user report", command = r.frequentUserScreen).grid(row=5, column = 0, sticky=W)
        popularreport = Button(r.selectscreen, text="popular book report", command = r.popularReportScreen).grid(row=6, column = 0, sticky=W)
        popularsubject = Button(r.selectscreen, text="popular subject report", command = r.popularSubjectScreen).grid(row=7, column = 0, sticky=W)
        logout = Button(r.selectscreen, text="Log out", command = lambda: r.toLogin('selectscreen')).grid(row=8, column = 0, sticky=W)

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
        r.checkoutissueEntry = Entry(r.checkoutscreen, width = 10)
        r.checkoutissueEntry.grid(row=1, column=1)
        username = Label(r.checkoutscreen, text="username ",bg="gold").grid(row =1, column=2,padx = 5, pady = 5,sticky = E)
        r.checkoutuserentry = Entry(r.checkoutscreen, width = 10)
        r.checkoutuserentry.grid(row=1, column=3)

        isbn = Label(r.checkoutscreen, text="isbn",bg="gold").grid(row =2, column=0,padx = 5, pady = 5,sticky = E)
        r.checkoutisbnentry = Entry(r.checkoutscreen, width = 10, state="readonly")
        r.checkoutisbnentry.grid(row=2, column=1)
        copy = Label(r.checkoutscreen, text="copy n",bg="gold").grid(row =2, column=2,padx = 5, pady = 5,sticky = E)
        r.checkoutcopyentry = Entry(r.checkoutscreen, width = 10, state="readonly")
        r.checkoutcopyentry.grid(row=2, column=3)

        checkout = Label(r.checkoutscreen, text="check out date",bg="gold").grid(row = 3, column=0,padx = 5, pady = 5,sticky = E)
        r.checkoutdateentry = Entry(r.checkoutscreen, width = 10, state="readonly")
        r.checkoutdateentry.grid(row=3, column=1)
        red = Label(r.checkoutscreen, text="estimated return date",bg="gold").grid(row =3, column=2,padx = 5, pady = 5,sticky = E)
        r.checkoutredentry = Entry(r.checkoutscreen, width = 10, state="readonly")
        r.checkoutredentry.grid(row=3, column=3)

        

        button = Button(r.checkoutscreen, text="confirm", command = r.checkOut).grid(row=4,column=3)
        toselect = Button(r.checkoutscreen, text="to select screen", command =  lambda: r.toSelectScreen('checkout')).grid(row=4,column=4)
    def checkOut(r):
        ## update red, ischeckedout, on hold
        issue_id = r.checkoutissueEntry.get()
        ## get isbn
        sql = "SELECT ISBN, COPY_N FROM ISSUED_BOOK WHERE ISSUED_ID = %s"
        r.cursor.execute(sql, issue_id)
        isbnAndcopy_n = r.cursor.fetchone()
        isbn = isbnAndcopy_n[0]
        copy_n = isbnAndcopy_n[1]
        today = datetime.date.today()
        red = today + datetime.timedelta(14)
        ## after 3 days -> drop

        sql = "SELECT DATE_OF_ISSUED FROM ISSUED_BOOK WHERE ISSUED_ID = %s"
        r.cursor.execute(sql, issue_id)
        dateofissue = r.cursor.fetchone()[0]
        if today > dateofissue + datetime.timedelta(3):
            sql = "UPDATE ISSUED_BOOK NATURAL JOIN BOOK_COPY SET IS_ON_HOLD = 'N' WHERE ISSUED_ID = %s AND USERNAME  = %s"
            r.cursor.execute(sql, (issue_id, r.checkoutuserentry.get()) )
            sql = "UPDATE BOOK_COPY SET IS_ON_HOLD = 'N' WHERE ISBN = %s AND COPY_N = %s"
            r.cursor.execute(sql, (isbn, copy_n) )
            messagebox.showerror('error!', 'user came late to check out! his hold has automatically been dropped')            
        ## no copy is available -> request for future
        else:
            ## update new redate
            sql = "UPDATE ISSUED_BOOK SET RETURN_DATE = CAST(%s AS DATETIME) WHERE ISSUED_ID = %s"
            r.cursor.execute(sql, (red, issue_id))
            r.db.commit()
            ## update is_checked_out, is_on_hold
            sql = "UPDATE BOOK_COPY SET IS_CHECKED_OUT = 'Y', IS_ON_HOLD = 'N' WHERE ISBN = %s AND COPY_N = %s"
            r.cursor.execute(sql, ( isbn, copy_n))
            r.db.commit()
            r.checkoutisbnentry.configure(state=NORMAL)
            r.checkoutisbnentry.insert(0, isbn)
            r.checkoutisbnentry.configure(state=DISABLED)
            r.checkoutcopyentry.configure(state=NORMAL)
            r.checkoutcopyentry.insert(0, copy_n)
            r.checkoutcopyentry.configure(state=DISABLED)
            r.checkoutdateentry.configure(state=NORMAL)
            r.checkoutdateentry.insert(0, str(today))
            r.checkoutdateentry.configure(state=DISABLED)
            r.checkoutredentry.configure(state=NORMAL)
            r.checkoutredentry.insert(0, str(red))
            r.checkoutredentry.configure(state=DISABLED)

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
        r.returnbookuserentry = Entry(r.returnbookscreen, width = 10)
        r.returnbookuserentry.grid(row=1, column=3)

        isbn = Label(r.returnbookscreen, text="isbn").grid(row =2, column=0,padx = 5, pady = 5,sticky = E)
        r.returnbookisbnentry = Entry(r.returnbookscreen, width = 10)
        r.returnbookisbnentry.grid(row=2, column=1)
        copy = Label(r.returnbookscreen, text="copy n").grid(row =2, column=2,padx = 5, pady = 5,sticky = E)
        r.returnbookcopyentry = Entry(r.returnbookscreen, width = 10)
        r.returnbookcopyentry.grid(row=2, column=3)

        damage = Label(r.returnbookscreen, text="return in damaged condition").grid(row = 3, column=0,padx = 5, pady = 5,sticky = E)
        r.returnbookdentry = Entry(r.returnbookscreen, width = 10)
        r.returnbookdentry.grid(row=3, column=1)
        
        button = Button(r.returnbookscreen, text="return", command = r.returnBook).grid(row=3,column=2)
        button2 = Button(r.returnbookscreen, text ="selection screen", command = lambda : r.toSelectScreen('returnbook')).grid(row=3,column = 3)

        r.root.mainloop()
        
    def returnBook(r):
        ## check damage of not, update
        issue_id = r.returnbookissueid.get()
        user = r.returnbookuserentry.get()
        isbn = r.returnbookisbnentry.get()
        copy_n = r.returnbookcopyentry.get()
        damaged = r.returnbookdentry.get().strip()
        
        #######FOR TEST PURPOSE I need to enter username, isbn, and copy_n#######
        sql = "SELECT USERNAME, ISBN, COPY_N FROM ISSUED_BOOK WHERE ISSUED_ID = %s"
        r.cursor.execute(sql, issue_id)
        l = r.cursor.fetchone()
        user= l[0]
        isbn = l[1]
        copy_n = l[2]
        print(user)
        print(isbn)
        print(copy_n)

        #################################################################################
        
        if (damaged == "Y" or damaged == "N"): ##check damaged
            if damaged == "Y":
                r.penaltyScreen()
            else:
                ##check late
                sql = "SELECT RETURN_DATE FROM ISSUED_BOOK WHERE ISBN = %s AND COPY_N = %s"
                r.cursor.execute(sql, (isbn, copy_n))
                red = r.cursor.fetchone()[0]
                print(red)
                if red >= datetime.date.today(): ## returned in correct day
                    sql = "UPDATE BOOK_COPY SET IS_CHECKED_OUT = %s WHERE ISBN = %s AND COPY_N = %s"
                    r.cursor.execute(sql, ("N", isbn, copy_n))
                    r.returnbookisbnentry.insert(0, isbn)
                    r.returnbookcopyentry.insert(0, copy_n)
                    messagebox.showerror("successful!", "successfully returned!")
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
                    messagebox.showerror("late!", "user is late to borrow book!")
        else:
            messagebox.showerror("error!","please enter damaged entry either 'Y' or 'N'!")

        r.db.commit()


    ##############################LOST / DAMAGED BOOK ####################
    def penaltyScreen(r):
        r.penaltyscreen = Toplevel(r.selectscreen)
        r.penaltyscreen.config(bg="gold")
        r.selectscreen.withdraw()
        title =  Label(r.penaltyscreen, text="Lost/Damaged Book ",bg="gold").grid(row =0, column=0,columnspan = 4,padx = 5, pady = 5,sticky = EW)
        isbn = Label(r.penaltyscreen, text="isbn ",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        r.penaltyisbnentry = Entry(r.penaltyscreen, width = 10)
        r.penaltyisbnentry.grid(row=1, column=1)


        copy = Label(r.penaltyscreen, text="Book copy#",bg="gold").grid(row =1 , column = 2)
        r.penaltycopyentry = IntVar()
        r.penaltycopyentry.set(1)
        sublist = r.showBookCopy()
        suboption1 = OptionMenu(r.penaltyscreen, r.penaltycopyentry, *sublist).grid(row=1,column=3)

        current = Label(r.penaltyscreen, text="current time",bg="gold").grid(row =2, column=0,padx = 5, pady = 5,sticky = E)
        r.penaltytimeentry = Entry(r.penaltyscreen, width = 10, state="readonly")
        r.penaltytimeentry.grid(row=2, column=1)

       
        lastuser = Label(r.penaltyscreen, text="last user",bg="gold").grid(row =4, column=0,padx = 5, pady = 5,sticky = E)
        r.lastuser = Entry(r.penaltyscreen, width = 10, state="readonly")
        r.lastuser.grid(row=4, column=1)

        amount = Label(r.penaltyscreen, text="amount to be charged",bg="gold").grid(row = 5, column=0,padx = 5, pady = 5,sticky = E)
        r.penaltyamount = Entry(r.penaltyscreen, width = 10)
        r.penaltyamount.grid(row=5, column=1)
        
        button = Button(r.penaltyscreen,bg="gold", text="look for the last user", command = r.lookForLastUser).grid(row=3,column=1)
        button2 = Button(r.penaltyscreen, text="submit", command = r.chargePenalty,bg="gold").grid(row=6,column=3)
        button3 = Button(r.penaltyscreen, text="cancel", command = lambda: r.toSelectScreen("penalty"),bg="gold").grid(row=6,column=4)
            
    def showBookCopy(r):
        sql = "SELECT DISTINCT COPY_N FROM BOOK_COPY"
        r.cursor.execute(sql)
        ll = r.cursor.fetchall()
        l = []
        for e in ll:
            l.append(e[0])
        return l
    
    def lookForLastUser(r):
        isbn = r.penaltyisbnentry.get()
        copy_n = r.penaltycopyentry.get()
        sql = "SELECT USERNAME FROM ISSUED_BOOK WHERE RETURN_DATE < CURDATE() AND ISBN = %s AND COPY_N = %s ORDER BY RETURN_DATE DESC LIMIT 1"
        r.cursor.execute(sql, (isbn, copy_n))
        user = r.cursor.fetchone()
        if user == None:
            messagebox.showerror("error!", "user doesn't exist!")
        else:
            user = user[0]
            r.lastuser.configure(state=NORMAL)
            r.lastuser.insert(0, user)
            r.lastuser.configure(state=DISABLED)
            r.penaltytimeentry.configure(state=NORMAL)
            r.penaltytimeentry.insert(0, str(datetime.date.today()))
            r.penaltytimeentry.configure(state=DISABLED)

    def chargePenalty(r):
        isbn = r.penaltyisbnentry.get()
        copy_n = r.penaltycopyentry.get()

        amount = r.penaltyamount.get()
        user = r.lastuser.get()

        sql = "SELECT PENALTY FROM STUDENT_FACULTY WHERE USERNAME = %s"
        r.cursor.execute(sql, user)
        addthis = r.cursor.fetchone()
        if addthis == None:
            messagebox.showerror('error!', 'enter information!')
        else:
            addthis = addthis[0]
        if amount == '':
            messagebox.showerror('error!', 'enter amount to charge!')
        else:
            amount = int(amount) + addthis
            
            sql = "UPDATE STUDENT_FACULTY SET PENALTY = %s WHERE USERNAME = %s"
            r.cursor.execute(sql, (amount, user))

            r.db.commit()
            messagebox.showerror("successfull!","penalty charged")
            
            r.penaltyisbnentry.delete(0, 'end')
            r.penaltytimeentry.config(state=NORMAL)
            r.penaltytimeentry.delete(0, 'end')
            r.lastuser.config(state=NORMAL)
            r.lastuser.delete(0, 'end')
            r.lastuser.config(state=DISABLED)
            r.penaltyamount.config(state=NORMAL)
            r.penaltyamount.delete(0, 'end')
            r.penaltyamount.config(state=DISABLED)
    
    ######################REPORT#####################
    ##########1.DAMAGED BOOK REPORT##################
    def damagedBookScreen(r):
        r.damagedbook = Toplevel(r.selectscreen)
        r.damagedbook.configure(bg="gold")    #### I tried to seperate windows with 2 sperate Frames, but if label is in a Frame, Frame's bg color doesn't function. I searched but they say OSX doesn't support it. do you have any solution?
        r.damagedbooklist = []
        label = Label(r.damagedbook, text="Damaged Book Report", bg = "gold")
        label.grid(row = 0,column = 0, columnspan = 4, padx = 5,pady=5,sticky=W)

        label2 = Label(r.damagedbook, text="Month :",bg="gold").grid(row =1, column=0)
        r.damagedbookv1 = StringVar()
        r.damagedbookv1.set('Jan')
        r.months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec']
        r.month = OptionMenu(r.damagedbook, r.damagedbookv1, *r.months)
        r.month.grid(row = 1, column = 1)
        
        label3 = Label(r.damagedbook, text="Subject:",bg="gold").grid(row =1 , column = 2)
        r.s1 = StringVar()
        r.s1.set('')
        sublist = r.showSubjects()
        suboption1 = OptionMenu(r.damagedbook, r.s1, *sublist).grid(row=1,column=3)

        label4 = Label(r.damagedbook, text="Subject:",bg="gold").grid(row =2 , column = 2)
        r.s2 = StringVar()
        r.s2.set('')
        suboption2 = OptionMenu(r.damagedbook, r.s2, *sublist).grid(row=2,column=3)


        label5 = Label(r.damagedbook, text="Subject:",bg="gold").grid(row =3 , column = 2)
        r.s3 = StringVar()
        r.s3.set('')
        suboption3 = OptionMenu(r.damagedbook, r.s3, *sublist).grid(row=3,column=3)

        
        label6 = Label(r.damagedbook, text = 'Month',bg="gold").grid(row=5, column = 0)
        label7 = Label(r.damagedbook, text = 'Subject',bg="gold").grid(row=5, column = 1)
        label8 = Label(r.damagedbook, text = '#damage books',bg="gold").grid(row=5, column = 2)
       


        button = Button(r.damagedbook, text="showResult", command = r.showResult).grid(row=4, column = 3)

    def showSubjects(r):
        month = r.damagedbookv1.get()
        sql = "SELECT SUBJECT_NAME FROM SUBJECT"
        r.cursor.execute(sql)
        ll = r.cursor.fetchall()
        l = []
        for e in ll:
            l.append(e[0])

        return l
    
    def showResult(r):
        month = r.damagedbookv1.get()
        cy = datetime.date.today().strftime("%Y")

        dic = {'Jan':1,'Feb':2,'Mar':3,'Apr':4,'May':5,'Jun':6,'Jul':7,'Aug':8,'Sep':9,'Oct':10,'Nov':11,'Dec':12}
         
        sql = '''(SELECT MONTH( ISSUED_BOOK.DATE_OF_ISSUED) AS  'Month', BOOK_BOOK.SUBJECT_NAME, COUNT( * ) AS  '#checkouts', ISSUED_BOOK.ISSUED_ID
        FROM BOOK_BOOK
        NATURAL JOIN ISSUED_BOOK
        NATURAL JOIN BOOK_COPY
        WHERE MONTH(ISSUED_BOOK.RETURN_DATE) = %s
        AND YEAR(ISSUED_BOOK.RETURN_DATE) = %s
        AND BOOK_COPY.IS_DAMAGED =  'Y'
        AND SUBJECT_NAME = %s
        GROUP BY SUBJECT_NAME
        LIMIT 1) UNION (SELECT MONTH( ISSUED_BOOK.DATE_OF_ISSUED) AS  'Month', BOOK_BOOK.SUBJECT_NAME, COUNT( * ) AS  '#checkouts', ISSUED_BOOK.ISSUED_ID
        FROM BOOK_BOOK
        NATURAL JOIN ISSUED_BOOK
        NATURAL JOIN BOOK_COPY
        WHERE MONTH(ISSUED_BOOK.RETURN_DATE) = %s
        AND YEAR(ISSUED_BOOK.RETURN_DATE) = %s
        AND BOOK_COPY.IS_DAMAGED =  'Y'
        AND SUBJECT_NAME = %s
        GROUP BY SUBJECT_NAME
        LIMIT 1) UNION (SELECT MONTH( ISSUED_BOOK.DATE_OF_ISSUED) AS  'Month', BOOK_BOOK.SUBJECT_NAME, COUNT( * ) AS  '#checkouts', ISSUED_BOOK.ISSUED_ID
        FROM BOOK_BOOK
        NATURAL JOIN ISSUED_BOOK
        NATURAL JOIN BOOK_COPY
        WHERE MONTH(ISSUED_BOOK.RETURN_DATE) = %s
        AND YEAR(ISSUED_BOOK.RETURN_DATE) = %s
        AND BOOK_COPY.IS_DAMAGED =  'Y'
        AND SUBJECT_NAME = %s
        GROUP BY SUBJECT_NAME
        LIMIT 1)'''
      
        ##cy = datetime.date.today().strftime("%Y")
        cy = datetime.date(2010, 1, 1).strftime("%Y")
        r.cursor.execute(sql, (dic[month], cy, r.s1.get(),dic[month], cy, r.s2.get(),dic[month], cy, r.s3.get()))
        tuplelist = r.cursor.fetchall()
        i = 6
        if len(r.damagedbooklist) == 0:

            for e in tuplelist:
                l = []
                l.append(Label(r.damagedbook, text = month))
                l[0].grid(row=i, column = 0)
                l.append(Label(r.damagedbook, text = e[1]))
                l[1].grid(row=i, column = 1)
                l.append(Label(r.damagedbook, text = e[2]))
                l[2].grid(row=i, column = 2)
                i += 1
                r.damagedbooklist.append(l)
        else:
            for e in r.damagedbooklist:
                for v in e:
                    v.grid_forget()
            
            r.damagedbooklist = []
            for e in tuplelist:
                l = []
                l.append(Label(r.damagedbook, text = month))
                l[0].grid(row=i, column = 0)
                l.append(Label(r.damagedbook, text = e[1]))
                l[1].grid(row=i, column = 1)
                l.append(Label(r.damagedbook, text = e[2]))
                l[2].grid(row=i, column = 2)
                i += 1
                r.damagedbooklist.append(l)
                
    ###################FREQUENT USER REPORT#######################
    def frequentUserScreen(r):
        
        r.frequentreport = Toplevel(r.selectscreen)
        r.frequentreport.configure(bg="gold")    #### I tried to seperate windows with 2 sperate Frames, but if label is in a Frame, Frame's bg color doesn't function. I searched but they say OSX doesn't support it. do you have any solution?

        label = Label(r.frequentreport, text="Frequent User Report", bg = "gold")
        label.grid(row = 0, columnspan = 4, padx = 5,pady=5,sticky=W)

        label2 = Label(r.frequentreport, text="Month-----",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        label3 = Label(r.frequentreport, text="UserName-----",bg="gold").grid(row = 1, column = 1,padx=5, pady = 5,sticky = E)
        label4 = Label(r.frequentreport, text="# of checkouts-",bg="gold").grid(row = 1, column = 2,padx=5, pady = 5,sticky = E)

        r.showFrequentUser()
        
    def showFrequentUser(r):
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
            labellist.append(Label(r.frequentreport, text = dic[int(e[0])]).grid(row=i, column = 0))
            labellist.append(Label(r.frequentreport, text = e[1]).grid(row=i, column = 1))
            labellist.append(Label(r.frequentreport, text = e[2]).grid(row=i, column = 2))
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

        r.showPopularBook()

    def showPopularBook(r):
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
        cy = datetime.date(2010, 1, 1).strftime("%Y")
        ##cy = datetime.date.today().strftime("%Y")
        feb = datetime.date(int(cy), 2, 14)
        mar = datetime.date(int(cy), 3, 14)
        jan  = datetime.date(int(cy), 1, 14)
        r.cursor.execute(sql, (feb, jan, mar, feb))
        tuplelist = r.cursor.fetchall()

        labellist = []
        i = 2
        dic = {1:'Jan',2:'Feb',3:'Mar',4:'Apr',5:'May',6:'Jun',7:'Jul',8:'Aug',9:'Sep',10:'Oct',11:'Nov',12:'Dec'}
  
        for e in tuplelist:
            labellist.append(Label(r.popularreport, text = dic[int(e[0])]).grid(row=i, column = 0))
            labellist.append(Label(r.popularreport, text = e[1]).grid(row=i, column = 1))
            labellist.append(Label(r.popularreport, text = e[2]).grid(row=i, column = 2))
            i += 1
    #####################POPULAR SUBJECT REPORT ####################
    def popularSubjectScreen(r):
        
        r.popularsubject = Toplevel(r.selectscreen)
        r.popularsubject.configure(bg="gold")    #### I tried to seperate windows with 2 sperate Frames, but if label is in a Frame, Frame's bg color doesn't function. I searched but they say OSX doesn't support it. do you have any solution?

        label = Label(r.popularsubject, text="Popular Subject Report", bg = "gold")
        label.grid(row = 0, columnspan = 4, padx = 5,pady=5,sticky=W)

        label2 = Label(r.popularsubject, text="Month-----",bg="gold").grid(row =1, column=0,padx = 5, pady = 5,sticky = E)
        label3 = Label(r.popularsubject, text="Top Subjects-----",bg="gold").grid(row = 1, column = 1,padx=5, pady = 5,sticky = E)
        label4 = Label(r.popularsubject, text="# of checkouts-",bg="gold").grid(row = 1, column = 2,padx=5, pady = 5,sticky = E)

        r.showPopularSubject()

    def showPopularSubject(r):
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
            labellist.append(Label(r.popularsubject, text = dic[int(e[0])]).grid(row=i, column = 0))
            labellist.append(Label(r.popularsubject, text = e[1]).grid(row=i, column = 1))
            labellist.append(Label(r.popularsubject, text = e[2]).grid(row=i, column = 2))
            i += 1


        


User()
