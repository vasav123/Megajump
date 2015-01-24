#megajumpg
from pygame import *
import re
screen=display.set_mode((600,600))
name= "redford"
idk= image.load("Characters/Redford/"+name+"_atlas.png").convert_alpha()

file= open("Charcter Textfiles/"+name+".txt").read().split("\n")
rects=[]
running=True
for i in file:
    x=i.replace("[","")
    x=x.replace("]","")
    a,b,c,d= map(int,x.split(","))
    rects.append(Rect(a,b,c,d))
print(rects)
count=0
while running:
    for e in event.get():
       if e.type==QUIT:
            running=False
    screen.blit(idk,(0,0))
    for r in rects:
        count+=1
        draw.rect(screen,(255,0,0),r,1)
        image.save(idk.subsurface(r),"Characters/Sheldon/"+name+str(count)+".png")
    running=False
    display.flip()
time.wait(1000)
quit()
