#megajumpg
from pygame import *
from random import *
screen=display.set_mode((479,600))
Background=Surface((479,600),SRCALPHA)
Background2=Surface((479,600),SRCALPHA)
bg=image.load("backgroundimage.png").convert()
# loading the images
balloon=[image.load("Android/balloon/android"+(str(i))+".png")for i in range(1,4)]
flyright=[image.load("Android/flyright/android"+str(i)+".png")for i in range(29,30)]
flyleft=[image.load("Android/flyleft/android"+str(i)+".png")for i in range(14,15)]
normal=[image.load("Android/android35.png")]
#deco loading
Deco=[image.load("Deco/"+str(i)+".png")for i in range(1,22)]
#badguys loading
purpdude=[image.load("Badguys/purpmonster/purp"+str(i)+".png")for i in range(1,21)]
reddude=[image.load("Badguys/redmonster/red"+str(i)+".png")for i in range(1,11)]
bluedude=[image.load("Badguys/bluemonster/blue"+str(i)+".png")for i in range(1,21)]
charcter=[balloon,flyright,flyleft,normal]
#loading some onscreen stuff
bigbluecoin=[image.load("gamelayerstuff/coins/bbcoin"+str(i)+".png")for i in range(1,7)]
# camera variables
x,y=0,0
vy=0
count=0
running=True
blitpos=[(0,randint(0,23040)) for i in range(10)]
def layer2blitting(x,y,blitpos):
    ''
##    for (bx,by) in (blitpos):
##        if 23040-600-y>by :
####            Background2.fill((0,0,0,0))
##           # Background2.blit(Deco[0],(0,-23040+600-y))

def drawbackground(x,y):
    Background.blit(bg,(0,-23040+600-y))
    screen.blit(Background,(0,0))
    screen.blit(Background2,(0,0))
    screen.blit(charcter[-1][int(count)%1],(451/2,450))
def camera(y,vy):
    if lift==False:
        if keys[K_UP]:
            return y,vy
    else:
        y-=vy
        return y,vy
lift=1
maxvy=0
liftoff=False
while running:
    for e in event.get():
        if e.type==QUIT:
            running=False
        if e.type==KEYDOWN:
            lift=False
        if e.type==KEYUP:
            maxvy=vy
            lift=True
    print(y)
    if abs(y)>=23040:
        y=0
    if lift==False:
        vy+=2
        liftoff=True
    elif lift and liftoff:
        if vy>=maxvy*-1:
            vy-=0.6
        else:
            vy=0
            liftoff=False
    if vy>100:
        vy=100
    count+=0.2
    keys=key.get_pressed()
    y,vy=camera(y,vy)
    layer2blitting(0,y,blitpos)
    drawbackground(0,y)
    display.flip()
quit()
