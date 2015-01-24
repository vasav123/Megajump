from pygame import *
backgrounds=[image.load("new"+str(i)+".png")for i in range(30)]
screen=display.set_mode((479,768*31))
running=True
while running:
    for e in event.get():
        if e.type==QUIT:
            running=False
    screen.blit(image.load("new29.png"),(0,0))
    for i in range(len(backgrounds)):
        screen.blit(backgrounds[i],(0,(i+1)*768))
    display.update()
    image.save(screen.subsurface(0,0,479,768*31),"final.png")
    display.flip()
    running=False
quit()
