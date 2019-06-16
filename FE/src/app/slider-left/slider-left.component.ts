import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { ShareService } from '../services/share.service';
declare var $: any;
@Component({
  selector: 'app-slider-left',
  templateUrl: './slider-left.component.html',
  styleUrls: ['./slider-left.component.scss']
})
export class SliderLeftComponent implements OnInit {
  hasAdmin = false
  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
    this.handleMenu()
    this.checkAdmin()
    ShareService.loginEvent.subscribe((_login) => {
      this.checkAdmin()
    })

  }
  onSelected(url: string): boolean {
    // console.log(url + ' ' + this.router.url);
    return (url === this.router.url) ? true : false;
  }
  handleMenu() {
    $(document).ready(function () {
      $('#cssmenu ul ul li:odd').addClass('odd');
      //$('#cssmenu ul ul li:even').addClass('even');
      $('#cssmenu > ul > li > a').click(function () {
        $('#cssmenu li').removeClass('active');
        $(this).closest('li').addClass('active');
        var checkElement = $(this).next();
        if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
          $(this).closest('li').removeClass('active');
          checkElement.slideUp('normal');
        }
        if ((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
          $('#cssmenu ul ul:visible').slideUp('normal');
          checkElement.slideDown('normal');
        }
        if ($(this).closest('li').find('ul').children().length == 0) {
          $('#cssmenu ul ul:visible').slideUp('normal');
          return true;
        } else {
          return false;
        }
      });
      $('.sub > a').click(function () {
        $('.sub li').removeClass('active');
        $(this).closest('li').addClass('active');
        var checkElement = $(this).next();
        if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
          $(this).closest('li').removeClass('active');
          checkElement.slideUp('normal');
        }
        if ((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
          $('.sub ul:visible').slideUp('normal');
          checkElement.slideDown('normal');
        }
        if ($(this).closest('li').find('ul').children().length == 0) {
          $('.sub ul:visible').slideUp('normal');
          return true;
        } else {
          return false;
        }
      });

      $(function () {
        $('.menu').click(function () {
          $('#css-menu').css({ right: '0' });
        });
        $('.close-menu').click(function () {
          $('#css-menu').css({ right: '-340px' });
        });
      });

    });


  }

  checkAdmin() {
    this.userService.isAdmin().subscribe((admin) => {
      this.hasAdmin = admin
    })
  }

}
