from pygame import *
screen=display.set_mode((479,600))

bg=image.load("backgroundimage.png").convert()
bigBar=image.load("menus/sliderBigBar.png")
smallBar=image.load("menus/sliderSmallBar.png")

running=True
bary=0
x,y=0,0
while running:
    for e in event.get():
        if e.type==QUIT:
            running=False
    screen.blit(bg,(x,int(bary*23808/582)*-1))
    screen.blit(bigBar,(462,0))
    screen.blit(smallBar,(462,bary))
    keys=key.get_pressed()
    print(bary)
    if keys[K_DOWN]:
        bary+=2
    if keys[K_UP]:
        bary-=2
    if bary<0:
        bary=0
    if bary>=545:
        bary=545
    display.flip()
quit()
